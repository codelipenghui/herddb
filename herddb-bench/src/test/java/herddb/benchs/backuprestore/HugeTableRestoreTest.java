/*
 Licensed to Diennea S.r.l. under one
 or more contributor license agreements. See the NOTICE file
 distributed with this work for additional information
 regarding copyright ownership. Diennea S.r.l. licenses this file
 to you under the Apache License, Version 2.0 (the
 "License"); you may not use this file except in compliance
 with the License.  You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 KIND, either express or implied.  See the License for the
 specific language governing permissions and limitations
 under the License.

 */
package herddb.benchs.backuprestore;

import java.io.IOException;
import java.util.Map;

import org.junit.Test;

import herddb.backup.ProgressListener;
import herddb.benchs.BaseBench;
import herddb.server.ServerConfiguration;

/**
 * Simple concurrent reads and writes on a single table
 *
 * @author enrico.olivelli
 */
public class HugeTableRestoreTest extends BaseBench {

    public HugeTableRestoreTest() {
        super(20,
            1_000_000,
            0,
            2);
    }

    @Override
    protected void makeServerConfiguration() throws IOException {
        super.makeServerConfiguration();
    }

    @Test
    public void run() throws Exception {
        generateData(1024);
        performOperations();
        waitForResults();
//        serverConfiguration.set(ServerConfiguration.PROPERTY_MAX_PAGES_MEMORY, 5 * 1024 * 1024);
//        serverConfiguration.set(ServerConfiguration.PROPERTY_MEMORY_LIMIT_REFERENCE, 5 * 1024 * 1024);
        restartServer();
        backupRestore(10000, new ProgressListener() {
            @Override
            public void log(String msgType, String message, Map<String, Object> context) {
                System.out.println("PROGESS " + msgType + " -------------------- " + message);
//                if (msgType.equals("sendtabledata")) {
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException ee) {
//                        throw new RuntimeException(ee);
//                    }
//                }
            }

        });
    }

}
