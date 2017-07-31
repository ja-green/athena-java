/*
 * Copyright (C) 2017 Jack Green
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.athena.utils;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Jack Green on 26/07/2017.
 */
public class FileUtils {
    private static InputStream is;

    public static int getLineCount(String filename) throws IOException {
        try {
            is = new FileInputStream(filename);
            LineNumberReader lnr = new LineNumberReader(new BufferedReader(new InputStreamReader(is, "UTF-8")));
            lnr.skip(Long.MAX_VALUE);
            return lnr.getLineNumber() + 1;

        } catch (IOException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        } finally {
            is.close();
        }
    }

    public static int getBytes(String filename) throws IOException {
        try {
            is = new FileInputStream(filename);
            return is.available();

        } catch (IOException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        } finally {
            is.close();
        }
    }

    public static int getUniques(String filename) throws IOException {
        try {
            String line;
            is = new FileInputStream(filename);
            ArrayList<String> uniqueLines = new ArrayList<String>();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            while ((line = reader.readLine()) != null) {
                if (!uniqueLines.contains(line)) {
                    uniqueLines.add(line);
                }
            }
            return uniqueLines.size();

        } catch (IOException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        } finally {
            is.close();
        }
    }

    public static byte[] getFileChunk(String filename) {
        FileInputStream f = null;
        try {
            f = new FileInputStream(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        FileChannel ch = f.getChannel();
        MappedByteBuffer mb = null;
        try {
            mb = ch.map(FileChannel.MapMode.READ_ONLY, 0L, ch.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] barray = new byte[8000];
        //long checkSum = 0L;
        int nGet;

        while(mb.hasRemaining()) {
            nGet = Math.min(mb.remaining(), 8000);
            mb.get(barray, 0, nGet);
            /*for (int i = 0; i < nGet; i++) {
                checkSum += barray[i];
            }*/
        }
        return barray;
    }
}