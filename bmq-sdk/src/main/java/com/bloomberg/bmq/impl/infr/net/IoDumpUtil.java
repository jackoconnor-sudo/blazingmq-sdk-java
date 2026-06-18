/*
 * Copyright 2022 Bloomberg Finance L.P.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bloomberg.bmq.impl.infr.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.invoke.MethodHandles;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for writing IO dump/diagnostic files.
 *
 * <p>When the {@code BMQ_IO_DUMP_DIR} environment variable is set and points to a writable
 * directory, this class records all raw network IO to a binary dump file and an accompanying index
 * file. The dump can be used for offline diagnostics and replay.
 */
final class IoDumpUtil {

    static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final int MAX_IO_DUMP_FILE_SIZE = 1024 * 1024 * 1024; // One Gb

    private static boolean dumpMode = false;

    private static FileOutputStream blackBox;
    private static Writer blackBoxIndex;

    static {
        String s = System.getenv("BMQ_IO_DUMP_DIR");
        if (s != null) {
            File dumpDir = new File(s);
            if (dumpDir.exists() && dumpDir.isDirectory() && dumpDir.canWrite()) {
                try {
                    String filePref = "bmq_io_dump_" + System.currentTimeMillis();
                    File binFile = new File(dumpDir, filePref + ".bin");
                    File idxFile = new File(dumpDir, filePref + ".idx");

                    blackBox = new FileOutputStream(binFile);
                    blackBoxIndex =
                            new OutputStreamWriter(
                                    new FileOutputStream(idxFile), StandardCharsets.US_ASCII);
                    dumpMode = true;
                    logger.info("Dump mode data  file: {}", binFile.getAbsolutePath());
                    logger.info("Dump mode index file: {}", idxFile.getAbsolutePath());
                } catch (IOException e) {
                    logger.error("Failed to enable IO dump mode: ", e);
                }
            } else {
                logger.error(
                        "Failed to enable IO dump mode. No such directory or not writable: {}", s);
            }
        }
    }

    private IoDumpUtil() {}

    static boolean isDumpMode() {
        return dumpMode;
    }

    static void dump(ByteBuffer[] byteBuffers) {
        try {
            if (blackBox.getChannel().size() >= MAX_IO_DUMP_FILE_SIZE) {
                logger.warn("Dump exceeded max size: {}", blackBox.getChannel().size());
                closeDumps();
                dumpMode = false;
                return;
            }
            blackBoxIndex.write(Integer.toString(byteBuffers.length));
            for (ByteBuffer b : byteBuffers) {
                blackBoxIndex.write(" " + b.remaining());
                while (b.hasRemaining()) {
                    blackBox.write(b.get());
                }
                b.rewind();
            }
            blackBoxIndex.write("\n");
        } catch (IOException e) {
            logger.error("Failed to do IO dump: ", e);
            closeDumps();
            dumpMode = false;
        }
    }

    static void closeDumps() {
        try {
            if (blackBox != null) {
                blackBox.close();
            }
            if (blackBoxIndex != null) {
                blackBoxIndex.close();
            }
        } catch (IOException e) {
            logger.error("Failed to close dumps: ", e);
        }
    }
}
