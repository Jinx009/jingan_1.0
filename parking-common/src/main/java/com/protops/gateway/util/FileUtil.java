package com.protops.gateway.util;


import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

/**
 * Wonderful file utilities you ever want! &lt;- 极好的 ..
 * @author chding
 */
public class FileUtil {
	
	public static final String DEFAULT_CHARSET = "UTF-8";
	public static final String LINE_SEPARATOR = (String) System.getProperty("line.separator");

	private static final String MODE_R = "r";
	private static final String MODE_RWS = "rws";

	private FileUtil() {}

    //////////////////////////// convenient methods - load file

    public static String loadFileAsString(String file) {
        return loadFileAsString(file, DEFAULT_CHARSET);
    }

    public static String loadFileAsString(String file, String charset) {
        return loadFileAsString(new File(file), charset);
    }

    public static String loadFileAsString(URL url) {
        return loadFileAsString(url, DEFAULT_CHARSET);
    }

    public static String loadFileAsString(URL url, String charset) {
        return loadFileAsString(loadFile(url), charset);
    }

    public static String loadFileAsString(File file, String charset) {
        byte[] content = loadFileAsByteArray(file);
        return newString(content, charset);
    }

    public static List<String> loadFileAsLines(String file) {
        return loadFileAsLines(file, DEFAULT_CHARSET);
    }

    public static List<String> loadFileAsLines(File file) {
        return loadFileAsLines(file, DEFAULT_CHARSET);
    }

    public static List<String> loadFileAsLines(URL url) {
        return loadFileAsLines(loadFile(url), DEFAULT_CHARSET);
    }

    public static List<String> loadFileAsLines(String file, String charset) {
        return loadFileAsLines(new File(file), charset);
    }

    public static byte[] loadFileAsByteArray(URL url) {
        return loadFileAsByteArray(loadFile(url));
    }

    public static byte[] loadFileAsByteArray(String file) {
        return loadFileAsByteArray(new File(file));
    }

    public static Properties loadFileAsProperties(String file) {
        Properties p = new Properties();
        boolean success = loadFileIntoProperties(p, file);
        return success ? p : null;
    }

    public static Properties loadFileAsProperties(URL url) {
        Properties p = new Properties();
        boolean success = loadFileIntoProperties(p, url);
        return success ? p : null;
    }

    public static Properties loadFileAsProperties(File file) {
        Properties p = new Properties();
        boolean success = loadFileIntoProperties(p, file);
        return success ? p : null;
    }

    public static boolean loadFileIntoProperties(Properties p, String file) {
        return loadFileIntoProperties(p, new File(file));
    }

    public static boolean loadFileIntoProperties(Properties p, URL url) {
        return loadFileIntoProperties(p, loadFile(url));
    }

    public static boolean loadFileIntoProperties(Properties p, File file) {
        return loadFileIntoProperties(p, file, DEFAULT_CHARSET);
    }

    public static Properties loadFileAsProperties(String file, String charset) {
        Properties p = new Properties();
        boolean success = loadFileIntoProperties(p, file, charset);
        return success ? p : null;
    }

    public static Properties loadFileAsProperties(File file, String charset) {
        Properties p = new Properties();
        boolean success = loadFileIntoProperties(p, file, charset);
        return success ? p : null;
    }

    public static boolean loadFileIntoProperties(Properties p, String file, String charset) {
        return loadFileIntoProperties(p, new File(file), charset);
    }


    //////////////////////////// convenient methods - save file

    public static void saveStringAsFile(String file, String content) {
        saveBytesAsFile(file, toBytes(content));
    }

    public static void saveStringAsFile(String file, String content, String charset) {
        saveBytesAsFile(file, toBytes(content, charset));
    }

    public static void saveLinesAsFile(String file, String[] content) {
        saveLinesAsFile(new File(file), content, DEFAULT_CHARSET);
    }

    public static void saveLinesAsFile(String file, String[] content, String charset) {
        saveLinesAsFile(new File(file), content, charset);
    }

    public static void saveLineAsFile(String file, String content) {
        saveLineAsFile(new File(file), content, DEFAULT_CHARSET);
    }

    public static void saveLineAsFile(File file, String content) {
        saveLineAsFile(file, content, DEFAULT_CHARSET);
    }

    public static void saveLineAsFile(String file, String content, String charset) {
        saveLineAsFile(new File(file), content, charset);
    }


    //////////////////////////// convenient methods - append to file

    public static void appendBytesToFile(String file, byte[] content) {
        appendBytesToFile(newRandomAccessFile(file, MODE_RWS), content);
    }

    public static void appendLineToFile(String file, String content) {
        appendStringToFile(file, content + LINE_SEPARATOR, DEFAULT_CHARSET);
    }

    public static void appendStringToFile(String file, String content) {
        appendStringToFile(file, content, DEFAULT_CHARSET);
    }

    public static void appendStringToFile(String file, String content, String charset) {
        appendStringToFile(newRandomAccessFile(file, MODE_RWS), content, charset);
    }

    public static void appendStringToFile(File file, String content, String charset) {
        appendStringToFile(newRandomAccessFile(file, MODE_RWS), content, charset);
    }

    public static void appendStringToFile(RandomAccessFile file, String content, String charset) {
        appendBytesToFile(file, toBytes(content, charset));
    }

    public static void appendLinesToFile(String file, Object[] content) {
        appendLinesToFile(new File(file), content, DEFAULT_CHARSET);
    }

    public static void appendLinesToFile(String file, Collection<Object> lines) {
        appendLinesToFile(new File(file), lines.toArray(new String[0]), DEFAULT_CHARSET);
    }

    public static void appendLinesToFile(File file, Object[] content) {
        appendLinesToFile(file, content, DEFAULT_CHARSET);
    }

    public static void appendLinesToFile(File file, List<Object> lines) {
        appendLinesToFile(file, lines.toArray(), DEFAULT_CHARSET);
    }

    public static void appendLinesToFile(String file, Object[] content, String charset) {
        appendLinesToFile(new File(file), content, charset);
    }

    public static void appendLinesToFile(String file, Collection<Object> lines, String charset) {
        appendLinesToFile(new File(file), lines.toArray(), charset);
    }

    public static void appendLinesToFile(File file, Collection<Object> lines, String charset) {
        appendLinesToFile(file, lines.toArray(), charset);
    }


    //////////////////////////// convenient methods - other

    public static boolean clearFile(String file) {
        return clearFile(new File(file));
    }

    public static long getFileSize(String file) {
        return getFileSize(new File(file));
    }


    //////////////////////////// base operations

    /**
     * 按行加载文件为字符串列表。
     * @param file
     * @param charset
     */
    public static List<String> loadFileAsLines(File file, String charset) {

        List<String> lines = new ArrayList<String>();
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));

            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            close(reader);
        }

        return lines;
    }

    /**
     * 加载文件为字节数组。
     * @param file
     */
    public static byte[] loadFileAsByteArray(File file) {
        RandomAccessFile r = newRandomAccessFile(file, MODE_R);

        try {

            byte[] b = new byte[(int) r.length()];
            r.read(b);

            return b;

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        } finally {
            close(r);
        }
    }

    /**
     * 加载配置文件[.properties]进属性[Properties]对象
     * @param p
     * @param file
     * @param charset
     */
    public static boolean loadFileIntoProperties(Properties p, File file, String charset) {

        InputStream is = null;

        try {
            is = new FileInputStream(file);
            p.load(new InputStreamReader(is, DEFAULT_CHARSET)); // 此句依赖JRE6
//            p.load(is);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        } finally {
            close(is);
        }
    }

    /**
     * 保存字节数组为一个文件。
     * @param file
     * @param content
     */
    public static void saveBytesAsFile(String file, byte[] content) {

        RandomAccessFile w = newRandomAccessFile(file, MODE_RWS);

        try {
            w.setLength(0);
            w.write(content);

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            close(w);
        }
    }

    /**
     * 保存一行字符串为一个文件。
     * @param file
     * @param content
     * @param charset
     */
    public static void saveLineAsFile(File file, String content, String charset) {

        PrintWriter writer = null;

        try {
            writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, false), charset));
            writer.println(content);
            writer.flush();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            close(writer);
        }
    }

    /**
     * 保存多行字符串为一个文件。
     * @param file
     * @param content
     * @param charset
     */
    public static void saveLinesAsFile(File file, String[] content, String charset) {

        PrintWriter writer = null;

        try {
            writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, false), charset));
            for (String line : content) {
                writer.println(line);
            }
            writer.flush();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            close(writer);
        }
    }

    /**
     * 附加字节到文件末尾。
     * @param file
     * @param content
     */
    public static void appendBytesToFile(RandomAccessFile file, byte[] content) {

        try {
            file.seek(file.length());
            file.write(content);

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            close(file);
        }
    }

    /**
     * 按行附加对象数组到文件末尾。
     * @param file
     * @param charset
     */
    public static void appendLinesToFile(File file, Object[] lines, String charset) {

        PrintWriter writer = null;

        try {
            writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, true), charset));

            for (Object line : lines) {
                writer.println(line);
            }

            writer.flush();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            close(writer);
        }
    }

    /**
     * 清空文件
     * @param file
     */
    public static boolean clearFile(File file) {

        RandomAccessFile w = newRandomAccessFile(file, MODE_RWS);

        try {
            w.setLength(0);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        } finally {
            close(w);
        }
    }

    public static long getFileSize(File file) {
        if (file.exists())
            return file.length();
        return 0;
    }

    //////////////////////////// utility methods

    public static File loadFile(URL url) {
        try {
            return new File(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String newString(byte[] src, String charset) {
        try {
            return new String(src, charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] toBytes(String src, String charset) {
        try {
            return src.getBytes(DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] toBytes(String src) {
        return toBytes(src, DEFAULT_CHARSET);
    }

    public static RandomAccessFile newRandomAccessFile(File file, String mode) {
        try {
            return new RandomAccessFile(file, mode);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static RandomAccessFile newRandomAccessFile(String file, String mode) {
        try {
            return new RandomAccessFile(file, mode);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void close(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
