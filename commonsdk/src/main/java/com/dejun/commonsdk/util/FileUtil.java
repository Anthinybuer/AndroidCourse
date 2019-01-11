package com.dejun.commonsdk.util;

import android.webkit.MimeTypeMap;

import com.orhanobut.logger.Logger;

import org.greenrobot.greendao.annotation.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Author:DoctorWei
 * Time:2018/12/10 15:51
 * Description:文件管理工具类 包括文件的复制进度回调 打开 和遍历
 * email:1348172474@qq.com
 */

public class FileUtil {
    /**
     * 文件的复制
     */
    public static void copyFile(String parentDir, String fileName, File originFile, CopyListener copyListener) {
        try {
            FileInputStream fileInputStream = new FileInputStream(originFile);
            copyFile(parentDir, fileName, fileInputStream, originFile.length(), copyListener);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件的复制
     */
    public static void copyFile(String parentDir, String fileName, InputStream inputStream, long totalLenth, CopyListener copyListener) {
        FileOutputStream fileOutputStream = null;
        try {
            copyListener.startCopy();
            File newFile = new File(parentDir + File.separator + fileName);
            fileOutputStream = new FileOutputStream(newFile);
            byte[] data = new byte[2048];
            int len = 0;
            long currentLenght = 0;
            while ((len = inputStream.read(data)) != -1) {
                fileOutputStream.write(data, 0, len);
                currentLenght += len;
                copyListener.progress((int) (currentLenght * 100 / totalLenth));
            }
            copyListener.finish(newFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public interface CopyListener {
        void startCopy();

        void progress(int progress);

        void finish(File file);
    }

    /**
     * 创建文件
     *
     * @param path     文件所在目录的目录名，如/java/test/0.txt,要在当前目录下创建一个文件名为1.txt的文件，<br>
     *                 则path为/java/test，fileName为1.txt
     * @param fileName 文件名
     * @return 文件新建成功则返回true
     */
    public static boolean createFile(@NotNull String path, @NotNull String fileName) {
        File file = new File(path + File.separator + fileName);
        if (file.exists()) {
            Logger.w("新建文件失败：file.exist()=" + file.exists());
            return false;
        } else {
            try {
                boolean isCreated = file.createNewFile();
                return isCreated;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    /**
     * 删除单个文件
     *
     * @param path     文件所在路径名
     * @param fileName 文件名
     * @return 删除成功则返回true
     */
    public static boolean deleteFile(@NotNull String path, @NotNull String fileName) {
        File file = new File(path + File.separator + fileName);
        if (file.exists()) {
            boolean isDeleted = file.delete();
            return isDeleted;
        } else {
            return false;
        }
    }


    /**
     * 根据文件名获得文件的扩展名
     *
     * @param fileName 文件名
     * @return 文件扩展名（不带点）
     */
    public static String getFileSuffix(@NotNull String fileName) {
        int index = fileName.lastIndexOf(".");
        String suffix = fileName.substring(index + 1, fileName.length());
        return suffix;
    }

    /**
     * 重命名文件
     *
     * @param oldPath 旧文件的绝对路径
     * @param newPath 新文件的绝对路径
     * @return 文件重命名成功则返回true
     */
    public static boolean renameTo(@NotNull String oldPath, @NotNull String newPath) {
        if (oldPath.equals(newPath)) {
            Logger.w("文件重命名失败：新旧文件名绝对路径相同！");
            return false;
        }
        File oldFile = new File(oldPath);
        File newFile = new File(newPath);

        boolean isSuccess = oldFile.renameTo(newFile);
        Logger.w("文件重命名是否成功：" + isSuccess);
        return isSuccess;
    }

    /**
     * 重命名文件
     *
     * @param oldFile 旧文件对象
     * @param newFile 新文件对象
     * @return 文件重命名成功则返回true
     */
    public static boolean renameTo(File oldFile, File newFile) {
        if (oldFile.equals(newFile)) {
            Logger.w("文件重命名失败：旧文件对象和新文件对象相同！");
            return false;
        }
        boolean isSuccess = oldFile.renameTo(newFile);
        Logger.w("文件重命名是否成功：" + isSuccess);
        return isSuccess;
    }

    /**
     * 重命名文件
     *
     * @param oldFile 旧文件对象，File类型
     * @param newName 新文件的文件名，String类型
     * @return 重命名成功则返回true
     */
    public static boolean renameTo(File oldFile, String newName) {
        File newFile = new File(oldFile.getParentFile() + File.separator + newName);
        boolean flag = oldFile.renameTo(newFile);
        return flag;
    }


    /**
     * 文件大小的格式化
     *
     * @param size 文件大小，单位为byte
     * @return 文件大小格式化后的文本
     */
    public static String formatSize(long size) {
        DecimalFormat df = new DecimalFormat("####.00");
        if (size < 1024) // 小于1KB
        {
            return size + "Byte";
        } else if (size < 1024 * 1024) // 小于1MB
        {
            float kSize = size / 1024f;
            return df.format(kSize) + "KB";
        } else if (size < 1024 * 1024 * 1024) // 小于1GB
        {
            float mSize = size / 1024f / 1024f;
            return df.format(mSize) + "MB";
        } else if (size < 1024L * 1024L * 1024L * 1024L) // 小于1TB
        {
            float gSize = size / 1024f / 1024f / 1024f;
            return df.format(gSize) + "GB";
        } else {
            return "size: error";
        }
    }


    /**
     * 获取某个路径下的文件列表
     *
     * @param path 文件路径
     * @return 文件列表File[] files
     */
    public static File[] getFileList(String path) {
        File file = new File(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                return files;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 获取某个目录下的文件列表
     *
     * @param directory 目录
     * @return 文件列表File[] files
     */
    public static File[] getFileList(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            return files;
        } else {
            return null;
        }
    }

    /**
     * 取得文件或文件夹大小
     */
    public static long getFileSize(File file) {
        long size = 0;
        if (!file.isDirectory()) { // 文件
            return file.length();
        }
        File files[] = file.listFiles(); // 文件夹（递归）
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                size = size + getFileSize(files[i]);
            } else {
                size = size + files[i].length();
            }
        }
        return size;
    }
    /**
     * 删除文件
     **/
    public void deleteFile(File f) {
        if (f.isDirectory()) {
            File[] files = f.listFiles();
            if (files != null && files.length > 0) {
                for (int i = 0; i < files.length; ++i) {
                    deleteFile(files[i]);
                }
            }
        }
        f.delete();
    }

    public static Map<String, String> FILE_TYPE_MAP = new HashMap<String, String>();


    static {
        getAllFileType();// 初始化文件类型信息
    }


    /**
     * 根据文件的头10位来区别文件类型
     * 实测 取前10位有重合问题 具体遇到具体解决
     * 这个需要慢慢测
     */
    private static void getAllFileType() {
        FILE_TYPE_MAP.put("b1bec8edbcfecfc2d4d8", "txt"); // JPEG (jpg)
        FILE_TYPE_MAP.put("ffd8ffe000104a464946", "jpg"); // JPEG (jpg)
        FILE_TYPE_MAP.put("ffd8ffe1339245786966", "jpg"); // JPEG (jpg)
        FILE_TYPE_MAP.put("89504e470d0a1a0a0000", "png"); // PNG (png)
        FILE_TYPE_MAP.put("47494638396126026f01", "gif"); // GIF (gif)
        FILE_TYPE_MAP.put("49492a00227105008037", "tif"); // TIFF (tif)
        FILE_TYPE_MAP.put("424d228c010000000000", "bmp"); // 16色位图(bmp)
        FILE_TYPE_MAP.put("424d8240090000000000", "bmp"); // 24位位图(bmp)
        FILE_TYPE_MAP.put("424d8e1b030000000000", "bmp"); // 256色位图(bmp)
        FILE_TYPE_MAP.put("41433130313500000000", "dwg"); // CAD (dwg)
        FILE_TYPE_MAP.put("3c21444f435459504520", "html"); // HTML (html)
        FILE_TYPE_MAP.put("3c21646f637479706520", "htm"); // HTM (htm)
        FILE_TYPE_MAP.put("48544d4c207b0d0a0942", "css"); // css
        FILE_TYPE_MAP.put("696b2e71623d696b2e71", "js"); // js
        FILE_TYPE_MAP.put("7b5c727466315c616e73", "rtf"); // Rich Text Format (rtf)
        FILE_TYPE_MAP.put("38425053000100000000", "psd"); // Photoshop (psd)
        FILE_TYPE_MAP.put("46726f6d3a203d3f6762", "eml"); // Email [Outlook Express 6] (eml)
        FILE_TYPE_MAP.put("d0cf11e0a1b11ae10000", "doc"); // MS Excel 注意：word、msi 和  excel的文件头一样
        FILE_TYPE_MAP.put("d0cf11e0a1b11ae10000", "vsd"); // Visio 绘图
        FILE_TYPE_MAP.put("5374616E64617264204A", "mdb"); // MS Access (mdb)
        FILE_TYPE_MAP.put("252150532D41646F6265", "ps");
        FILE_TYPE_MAP.put("255044462d312e350d0a", "pdf"); // Adobe Acrobat (pdf)
        FILE_TYPE_MAP.put("2e524d46000000120001", "rmvb"); // rmvb/rm相同
        FILE_TYPE_MAP.put("464c5601050000000900", "flv"); // flv与f4v相同
        FILE_TYPE_MAP.put("00000020667479706d70", "mp4");
        FILE_TYPE_MAP.put("00000020667479706973", "mp4");
        FILE_TYPE_MAP.put("49443303000000002176", "mp3");
        FILE_TYPE_MAP.put("000001ba210001000180", "mpg"); //
        FILE_TYPE_MAP.put("3026b2758e66cf11a6d9", "wmv"); // wmv与asf相同
        FILE_TYPE_MAP.put("52494646e27807005741", "wav"); // Wave (wav)
        FILE_TYPE_MAP.put("52494646d07d60074156", "avi");
        FILE_TYPE_MAP.put("4d546864000000060001", "mid"); // MIDI (mid)
        FILE_TYPE_MAP.put("504b0304140000000800", "zip");
        FILE_TYPE_MAP.put("526172211a0700cf9073", "rar");
        FILE_TYPE_MAP.put("235468697320636f6e66", "ini");
        FILE_TYPE_MAP.put("504b03040a0000000000", "jar");
        FILE_TYPE_MAP.put("4d5a9000030000000400", "exe");// 可执行文件
        FILE_TYPE_MAP.put("3c25402070616765206c", "jsp");// jsp文件
        FILE_TYPE_MAP.put("4d616e69666573742d56", "mf");// MF文件
        FILE_TYPE_MAP.put("3c3f786d6c2076657273", "xml");// xml文件
        FILE_TYPE_MAP.put("494e5345525420494e54", "sql");// xml文件
        FILE_TYPE_MAP.put("7061636b616765207765", "java");// java文件
        FILE_TYPE_MAP.put("406563686f206f66660d", "bat");// bat文件
        FILE_TYPE_MAP.put("1f8b080045d4ba570203", "gz");// gz文件
        FILE_TYPE_MAP.put("1f8b0800000000000000", "gz");// gz文件
        FILE_TYPE_MAP.put("6c6f67346a2e726f6f74", "properties");// bat文件
        FILE_TYPE_MAP.put("cafebabe0000002e0041", "class");// bat文件
        FILE_TYPE_MAP.put("49545346030000006000", "chm");// bat文件
        FILE_TYPE_MAP.put("04000000010000001300", "mxp");// bat文件
        FILE_TYPE_MAP.put("504b0304140006000800", "docx");// docx文件
        FILE_TYPE_MAP.put("d0cf11e0a1b11ae10000", "wps");// WPS文字wps、表格et、演示dps都是一样的
        FILE_TYPE_MAP.put("6431303a637265617465", "torrent");
        FILE_TYPE_MAP.put("6D6F6F76", "mov"); // Quicktime (mov)
        FILE_TYPE_MAP.put("FF575043", "wpd"); // WordPerfect (wpd)
        FILE_TYPE_MAP.put("CFAD12FEC5FD746F", "dbx"); // Outlook Express (dbx)
        FILE_TYPE_MAP.put("2142444E", "pst"); // Outlook (pst)
        FILE_TYPE_MAP.put("AC9EBD8F", "qdf"); // Quicken (qdf)
        FILE_TYPE_MAP.put("E3828596", "pwl"); // Windows Password (pwl)
        FILE_TYPE_MAP.put("2E7261FD", "ram"); // Real Audio (ram)
        FILE_TYPE_MAP.put("000000000000000000000000", "txt"); // txt 文本文档
    }


    /**
     * 获取上传文件的文件头
     *
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder sb = new StringBuilder();
        if (src == null || src.length < 1) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;// 位运算 java中二进制采用补码形式
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                sb.append("0");
            }
            sb.append(hv);
        }
        return sb.toString();
    }

    public static String getFileType(String filePath) {
        String res = null;
        InputStream inputStream = null;
        try {
            if (filePath.startsWith("http")) {
                URL url = new URL(filePath);
                inputStream = url.openStream();
            } else {
                inputStream = new FileInputStream(filePath);
            }
            byte[] b = new byte[10];
            inputStream.read(b, 0, b.length);
            String fileCode = bytesToHexString(b);
            Logger.d(fileCode);
//这种方法在字典的头代码不够位数的时候可以用但是速度相对慢一点
            Iterator<String> keyIter = FILE_TYPE_MAP.keySet().iterator();
            while (keyIter.hasNext()) {
                String key = keyIter.next();
                if (key.toLowerCase().startsWith(fileCode.toLowerCase()) || fileCode.toLowerCase().startsWith(key.toLowerCase())) {
                    res = FILE_TYPE_MAP.get(key);
                    break;
                }
            }
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Logger.d(res);
        return res;
    }
    /**
     * 根据文件后缀名获得对应的MIME类型。
     *
     * @param file
     */
    public static final String TYPE_ANY = "all";
    public static final String TYPE_TXT = "txt";
    public static final String TYPE_IMAGE = "image";
    public static final String TYPE_VIDEO = "video";
    public static final String TYPE_AUDIO = "audio";
    public static final String TYPE_ZIP = "zip";
    public static final String TYPE_APK = "apk";

    /**
     * @param file
     *            文件
     *
     *            通过文件名称 获取文件图标以及类型
     *
     */
    public static String[] getFileIconAndTypeName(File file) {
        // 定义数组保存文件类型以及图标 iconAndTypes[0] 表示文件图标 iconAndTypes[1] 文件类型
        String[] iconAndTypes = new String[2];
        // 首先设置默认文件图标以及类型
        iconAndTypes[0] = "icon_file";
        iconAndTypes[1] = "*/*";
        if (file.isFile()) {
            // 根据文件名称进行判断
            String name = file.getName();
            int dotIndex = name.lastIndexOf(".");
            if (dotIndex < 0) {
                return iconAndTypes;
            }
            //获取文件后缀名称
            String end = name.substring(dotIndex + 1, name.length())
                    .toLowerCase();
            if (end.equals("")) {
                return iconAndTypes;
            }

            // 对  ICON_TYPE_Table进行便利   找到对应图标以及类型
            for (int i = 0; i < ICON_TYPE_Table.length; i++) {
                if (end.equals(ICON_TYPE_Table[i][0])) {
                    iconAndTypes[0] = ICON_TYPE_Table[i][1];
                    iconAndTypes[1] = ICON_TYPE_Table[i][2];
                }
            }
            return iconAndTypes;
        }
        return iconAndTypes;
    }
    public static String getFileIconAndTypeName(String path) {
        String fileType = FileUtil.getFileType(path);
        // 对  ICON_TYPE_Table进行便利   找到对应图标以及类型
            for (int i = 0; i < ICON_TYPE_Table.length; i++) {
                if (fileType.equals(ICON_TYPE_Table[i][0])) {
                    String type = ICON_TYPE_Table[i][2];
                    return type;
                }
            }
        return "";
    }
    private static final String[][] ICON_TYPE_Table = {
            // {文件后缀名，文件对应图像名称,　文件所属类型}
            { "apk", "icon_video", TYPE_APK },
            { "3gp", "icon_video", TYPE_VIDEO },
            { "aif", "icon_audio", TYPE_AUDIO },
            { "aifc", "icon_audio", TYPE_AUDIO },
            { "aiff", "icon_audio", TYPE_AUDIO },
            { "als", "icon_audio", TYPE_AUDIO },
            { "asc", "icon_text_plain", TYPE_TXT },
            { "asf", "icon_video", TYPE_VIDEO },
            { "asx", "icon_video", TYPE_VIDEO },
            { "au", "icon_audio", TYPE_AUDIO },
            { "avi", "icon_video", TYPE_VIDEO },
            { "awb", "icon_audio", TYPE_AUDIO },
            { "bmp", "icon_bmp", TYPE_IMAGE },
            { "bz2", "icon_archive", TYPE_ZIP }, { "c", "icon_c", TYPE_TXT },
            { "cpp", "icon_cpp", TYPE_TXT }, { "css", "icon_css", TYPE_TXT },
            { "dhtml", "icon_html", TYPE_TXT },
            { "doc", "icon_doc", TYPE_TXT }, { "dot", "icon_doc", TYPE_TXT },
            { "es", "icon_audio", TYPE_AUDIO },
            { "esl", "icon_audio", TYPE_AUDIO },
            { "fvi", "icon_video", TYPE_VIDEO },
            { "gif", "icon_gif", TYPE_IMAGE }, { "gz", "icon_gzip", TYPE_ZIP },
            { "h", "icon_c_h", TYPE_TXT }, { "htm", "icon_html", TYPE_TXT },
            { "html", "icon_html", TYPE_TXT },
            { "hts", "icon_html", TYPE_TXT },
            { "ico", "icon_ico", TYPE_IMAGE },
            { "ief", "icon_image", TYPE_IMAGE },
            { "ifm", "icon_gif", TYPE_IMAGE },
            { "ifs", "icon_image", TYPE_IMAGE },
            { "imy", "icon_audio", TYPE_AUDIO },
            { "it", "icon_audio", TYPE_AUDIO },
            { "itz", "icon_audio", TYPE_AUDIO },
            { "j2k", "icon_jpeg", TYPE_IMAGE },
            { "java", "icon_java", TYPE_TXT },
            { "jar", "icon_java", TYPE_ZIP },
            { "jnlp", "icon_java", TYPE_TXT },
            { "jpe", "icon_jpeg", TYPE_IMAGE },
            { "jpeg", "icon_jpeg", TYPE_IMAGE },
            { "jpg", "icon_jpeg", TYPE_IMAGE },
            { "jpz", "icon_jpeg", TYPE_IMAGE },
            { "js", "icon_javascript", TYPE_TXT },
            { "lsf", "icon_video", TYPE_VIDEO },
            { "lsx", "icon_video", TYPE_VIDEO },
            { "m15", "icon_audio", TYPE_AUDIO },
            { "m3u", "icon_playlist", TYPE_AUDIO },
            { "m3url", "icon_playlist", TYPE_AUDIO },
            { "ma1", "icon_audio", TYPE_AUDIO },
            { "ma2", "icon_audio", TYPE_AUDIO },
            { "ma3", "icon_audio", TYPE_AUDIO },
            { "ma5", "icon_audio", TYPE_AUDIO },
            { "mdz", "icon_audio", TYPE_AUDIO },
            { "mid", "icon_audio", TYPE_AUDIO },
            { "midi", "icon_audio", TYPE_AUDIO },
            { "mil", "icon_image", TYPE_IMAGE },
            { "mio", "icon_audio", TYPE_AUDIO },
            { "mng", "icon_video", TYPE_VIDEO },
            { "mod", "icon_audio", TYPE_AUDIO },
            { "mov", "icon_video", TYPE_VIDEO },
            { "movie", "icon_video", TYPE_VIDEO },
            { "mp2", "icon_mp3", TYPE_AUDIO },
            { "mp3", "icon_mp3", TYPE_AUDIO },
            { "mp4", "icon_video", TYPE_VIDEO },
            { "mpe", "icon_video", TYPE_VIDEO },
            { "mpeg", "icon_video", TYPE_VIDEO },
            { "mpg", "icon_video", TYPE_VIDEO },
            { "mpg4", "icon_video", TYPE_VIDEO },
            { "mpga", "icon_mp3", TYPE_AUDIO },
            { "nar", "icon_zip", TYPE_ZIP },
            { "nbmp", "icon_image", TYPE_IMAGE },
            { "nokia-op-logo", "icon_image", TYPE_IMAGE },
            { "nsnd", "icon_audio", TYPE_AUDIO },
            { "pac", "icon_audio", TYPE_AUDIO },
            { "pae", "icon_audio", TYPE_AUDIO },
            { "pbm", "icon_bmp", TYPE_IMAGE },
            { "pcx", "icon_image", TYPE_IMAGE },
            { "pda", "icon_image", TYPE_IMAGE },
            { "pdf", "icon_pdf", TYPE_TXT },
            { "pgm", "icon_image", TYPE_IMAGE },
            { "pict", "icon_image", TYPE_IMAGE },
            { "png", "icon_png", TYPE_IMAGE },
            { "pnm", "icon_image", TYPE_IMAGE },
            { "pnz", "icon_png", TYPE_IMAGE }, { "pot", "icon_ppt", TYPE_TXT },
            { "ppm", "icon_image", TYPE_IMAGE },
            { "pps", "icon_ppt", TYPE_TXT }, { "ppt", "icon_ppt", TYPE_TXT },
            { "pvx", "icon_video", TYPE_VIDEO },
            { "qcp", "icon_audio", TYPE_AUDIO },
            { "qt", "icon_video", TYPE_VIDEO },
            { "qti", "icon_image", TYPE_IMAGE },
            { "qtif", "icon_image", TYPE_IMAGE },
            { "ra", "icon_audio", TYPE_AUDIO },
            { "ram", "icon_audio", TYPE_AUDIO },
            { "rar", "icon_rar", TYPE_ZIP },
            { "ras", "icon_image", TYPE_IMAGE },
            { "rf", "icon_image", TYPE_IMAGE },
            { "rgb", "icon_image", TYPE_IMAGE },
            { "rm", "icon_audio", TYPE_AUDIO },
            { "rmf", "icon_audio", TYPE_AUDIO },
            { "rmm", "icon_audio", TYPE_AUDIO },
            { "rmvb", "icon_audio", TYPE_AUDIO },
            { "rp", "icon_image", TYPE_IMAGE },
            { "rpm", "icon_audio", TYPE_AUDIO },
            { "rtf", "icon_text_richtext", TYPE_TXT },
            { "rv", "icon_video", TYPE_VIDEO },
            { "s3m", "icon_audio", TYPE_AUDIO },
            { "s3z", "icon_audio", TYPE_AUDIO },
            { "shtml", "icon_html", TYPE_TXT },
            { "si6", "icon_image", TYPE_IMAGE },
            { "si7", "icon_image", TYPE_IMAGE },
            { "si9", "icon_image", TYPE_IMAGE },
            { "smd", "icon_audio", TYPE_AUDIO },
            { "smz", "icon_audio", TYPE_AUDIO },
            { "snd", "icon_audio", TYPE_AUDIO },
            { "stm", "icon_audio", TYPE_AUDIO },
            { "svf", "icon_image", TYPE_IMAGE },
            { "svg", "icon_image", TYPE_IMAGE },
            { "svh", "icon_image", TYPE_IMAGE },
            { "swf", "icon_flash", TYPE_VIDEO },
            { "swfl", "icon_flash", TYPE_VIDEO },
            { "tar", "icon_tar", TYPE_ZIP }, { "taz", "icon_tar", TYPE_ZIP },
            { "tgz", "icon_tar", TYPE_ZIP },
            { "tif", "icon_tiff", TYPE_IMAGE },
            { "tiff", "icon_tiff", TYPE_IMAGE },
            { "toy", "icon_image", TYPE_IMAGE },
            { "tsi", "icon_audio", TYPE_AUDIO },
            { "txt", "icon_text_plain", TYPE_TXT },
            { "ult", "icon_audio", TYPE_AUDIO },
            { "vdo", "icon_video", TYPE_VIDEO },
            { "vib", "icon_audio", TYPE_AUDIO },
            { "viv", "icon_video", TYPE_VIDEO },
            { "vivo", "icon_video", TYPE_VIDEO },
            { "vox", "icon_audio", TYPE_AUDIO },
            { "vqe", "icon_audio", TYPE_AUDIO },
            { "vqf", "icon_audio", TYPE_AUDIO },
            { "vql", "icon_audio", TYPE_AUDIO },
            { "wav", "icon_wav", TYPE_VIDEO },
            { "wax", "icon_audio", TYPE_AUDIO },
            { "wbmp", "icon_bmp", TYPE_IMAGE },
            { "wi", "icon_image", TYPE_IMAGE },
            { "wm", "icon_video", TYPE_VIDEO },
            { "wma", "icon_wma", TYPE_AUDIO },
            { "wmv", "icon_video", TYPE_VIDEO },
            { "wmx", "icon_video", TYPE_VIDEO },
            { "wpng", "icon_png", TYPE_IMAGE },
            { "wv", "icon_video", TYPE_VIDEO },
            { "wvx", "icon_video", TYPE_VIDEO },
            { "xbm", "icon_bmp", TYPE_IMAGE },
            { "xht", "icon_xhtml_xml", TYPE_TXT },
            { "xhtm", "icon_xhtml_xml", TYPE_TXT },
            { "xhtml", "icon_xhtml_xml", TYPE_TXT },
            { "xla", "icon_xls", TYPE_TXT }, { "xlc", "icon_xls", TYPE_TXT },
            { "xlm", "icon_xls", TYPE_TXT }, { "xls", "icon_xls", TYPE_TXT },

            { "xlt", "icon_xls", TYPE_TXT }, { "xlw", "icon_xls", TYPE_TXT },
            { "xm", "icon_audio", TYPE_AUDIO },
            { "xml", "icon_xml", TYPE_TXT },
            { "xmz", "icon_audio", TYPE_AUDIO },
            { "xpm", "icon_image", TYPE_IMAGE },
            { "xsit", "icon_xml", TYPE_TXT }, { "xsl", "icon_xml", TYPE_TXT },
            { "xwd", "icon_image", TYPE_IMAGE },
            { "zip", "icon_zip", TYPE_ZIP } };
    public static String getMIMEType(File file) {
        String type = "*/*";
        String fName = file.getName();
        // 获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }
		/* 获取文件的后缀名 */
        String end = fName.substring(dotIndex + 1, fName.length())
                .toLowerCase();
        if (end == "")
            return type;
        // 在MIME和文件类型的匹配表中找到对应的MIME类型。
        for (int i = 0; i < MIME_Table.length; i++) {
            if (end.equals(MIME_Table[i][0]))
                type = MIME_Table[i][1];
        }
        return type;
    }
    /** 文件MIME类型(主要用做打开操作时，指定打开的指定文件对应所属的MIME类型) */
    private static final String[][] MIME_Table = {
            // {后缀名，MIME类型}
            { "aab", "application/x-authoware-bin" },
            { "aam", "application/x-authoware-map" },
            { "aas", "application/x-authoware-seg" },
            { "amc", "application/x-mpeg" },
            { "ani", "application/octet-stream" },
            { "apk", "application/vnd.android.package-archive" },
            { "asd", "application/astound" }, { "asn", "application/astound" },
            { "asp", "application/x-asap" },
            { "ai", "application/postscript" },
            { "avb", "application/octet-stream" },
            { "bcpio", "application/x-bcpio" },
            { "bin", "application/octet-stream" },
            { "bld", "application/bld" }, { "bld2", "application/bld2" },
            { "aif", "audio/x-aiff" }, { "aifc", "audio/x-aiff" },
            { "aiff", "audio/x-aiff" }, { "als", "audio/X-Alpha5" },
            { "au", "audio/basic" }, { "awb", "audio/amr-wb" },
            { "3gp", "video/3gpp" }, { "asf", "video/x-ms-asf" },
            { "asx", "video/x-ms-asf" }, { "avi", "video/x-msvideo" },
            { "asc", "text/plain" }, { "bmp", "image/bmp" },
            { "bpk", "application/octet-stream" },
            { "bz2", "application/x-bzip2" }, { "c", "text/x-csrc" },
            { "cpp", "text/x-c++src" }, { "cal", "image/x-cals" },
            { "ccn", "application/x-cnc" }, { "cco", "application/x-cocoa" },
            { "cdf", "application/x-netcdf" },
            { "cgi", "magnus-internal/cgi" }, { "chat", "application/x-chat" },
            { "class", "application/octet-stream" },
            { "clp", "application/x-msclip" }, { "cmx", "application/x-cmx" },
            { "co", "application/x-cult3d-object" },
            { "cod", "image/cis-cod" }, { "csh", "application/x-csh" },
            { "csm", "chemical/x-csml" }, { "csml", "chemical/x-csml" },
            { "css", "text/css" }, { "dcm", "x-lml/x-evm" },
            { "cpio", "application/x-cpio" },
            { "cpt", "application/mac-compactpro" },
            { "crd", "application/x-mscardfile" },
            { "cur", "application/octet-stream" },
            { "dcr", "application/x-director" },
            { "dir", "application/x-director" },
            { "dll", "application/octet-stream" },
            { "dmg", "application/octet-stream" },
            { "dms", "application/octet-stream" },
            { "doc", "application/msword" }, { "dot", "application/x-dot" },
            { "dvi", "application/x-dvi" }, { "dwg", "application/x-autocad" },
            { "dxf", "application/x-autocad" },
            { "dxr", "application/x-director" },
            { "ebk", "application/x-expandedbook" },
            { "etc", "application/x-earthtime" }, { "dcx", "image/x-dcx" },
            { "dhtml", "text/html" }, { "dwf", "drawing/x-dwf" },
            { "emb", "chemical/x-embl-dl-nucleotide" },
            { "embl", "chemical/x-embl-dl-nucleotide" },
            { "eps", "application/postscript" }, { "eri", "image/x-eri" },
            { "es", "audio/echospeech" }, { "esl", "audio/echospeech" },
            { "etx", "text/x-setext" }, { "evm", "x-lml/x-evm" },
            { "evy", "application/x-envoy" },
            { "exe", "application/octet-stream" },
            { "fh4", "image/x-freehand" }, { "fh5", "image/x-freehand" },
            { "fhc", "image/x-freehand" }, { "fif", "image/fif" },
            { "fm", "application/x-maker" }, { "fpx", "image/x-fpx" },
            { "fvi", "video/isivideo" },
            { "gau", "chemical/x-gaussian-input" },
            { "gca", "application/x-gca-compressed" },
            { "gdb", "x-lml/x-gdb" }, { "gif", "image/gif" },
            { "gps", "application/x-gps" }, { "gtar", "application/x-gtar" },
            { "gz", "application/x-gzip" }, { "h", "text/x-chdr" },
            { "hdf", "application/x-hdf" }, { "hdm", "text/x-hdml" },
            { "hdml", "text/x-hdml" }, { "hlp", "application/winhlp" },
            { "hqx", "application/mac-binhex40" }, { "htm", "text/html" },
            { "html", "text/html" }, { "hts", "text/html" },
            { "ice", "x-conference/x-cooltalk" },
            { "ico", "application/octet-stream" }, { "ief", "image/ief" },
            { "ifm", "image/gif" }, { "ifs", "image/ifs" },
            { "imy", "audio/melody" }, { "ins", "application/x-NET-Install" },
            { "ips", "application/x-ipscript" },
            { "ipx", "application/x-ipix" }, { "it", "audio/x-mod" },
            { "itz", "audio/x-mod" }, { "ivr", "i-world/i-vrml" },
            { "j2k", "image/j2k" },
            { "jad", "text/vnd.sun.j2me.app-descriptor" },
            { "jam", "application/x-jam" }, { "java", "application/x-java" },
            { "jar", "application/java-archive" },
            { "jnlp", "application/x-java-jnlp-file" },
            { "jpe", "image/jpeg" }, { "jpeg", "image/jpeg" },
            { "jpg", "image/jpeg" }, { "jpz", "image/jpeg" },
            { "js", "application/x-javascript" }, { "jwc", "application/jwc" },
            { "kjx", "application/x-kjx" }, { "lak", "x-lml/x-lak" },
            { "latex", "application/x-latex" },
            { "lcc", "application/fastman" },
            { "lcl", "application/x-digitalloca" },
            { "lcr", "application/x-digitalloca" },
            { "lgh", "application/lgh" },
            { "lha", "application/octet-stream" }, { "lml", "x-lml/x-lml" },
            { "lmlpack", "x-lml/x-lmlpack" }, { "lsf", "video/x-ms-asf" },
            { "lsx", "video/x-ms-asf" }, { "lzh", "application/x-lzh" },
            { "m13", "application/x-msmediaview" },
            { "m14", "application/x-msmediaview" }, { "m15", "audio/x-mod" },
            { "m3u", "audio/x-mpegurl" }, { "m3url", "audio/x-mpegurl" },
            { "ma1", "audio/ma1" }, { "ma2", "audio/ma2" },
            { "ma3", "audio/ma3" }, { "ma5", "audio/ma5" },
            { "man", "application/x-troff-man" },
            { "map", "magnus-internal/imagemap" },
            { "mbd", "application/mbedlet" },
            { "mct", "application/x-mascot" },
            { "mdb", "application/x-msaccess" }, { "mdz", "audio/x-mod" },
            { "me", "application/x-troff-me" }, { "mel", "text/x-vmel" },
            { "mi", "application/x-mif" }, { "mid", "audio/midi" },
            { "midi", "audio/midi" }, { "mif", "application/x-mif" },
            { "mil", "image/x-cals" }, { "mio", "audio/x-mio" },
            { "mmf", "application/x-skt-lbs" }, { "mng", "video/x-mng" },
            { "mny", "application/x-msmoney" },
            { "moc", "application/x-mocha" },
            { "mocha", "application/x-mocha" }, { "mod", "audio/x-mod" },
            { "mof", "application/x-yumekara" },
            { "mol", "chemical/x-mdl-molfile" },
            { "mop", "chemical/x-mopac-input" }, { "mov", "video/quicktime" },
            { "movie", "video/x-sgi-movie" }, { "mp2", "audio/x-mpeg" },
            { "mp3", "audio/x-mpeg" }, { "mp4", "video/mp4" },
            { "mpc", "application/vnd.mpohun.certificate" },
            { "mpe", "video/mpeg" }, { "mpeg", "video/mpeg" },
            { "mpg", "video/mpeg" }, { "mpg4", "video/mp4" },
            { "mpga", "audio/mpeg" },
            { "mpn", "application/vnd.mophun.application" },
            { "mpp", "application/vnd.ms-project" },
            { "mps", "application/x-mapserver" }, { "mrl", "text/x-mrml" },
            { "mrm", "application/x-mrm" }, { "ms", "application/x-troff-ms" },
            { "mts", "application/metastream" },
            { "mtx", "application/metastream" },
            { "mtz", "application/metastream" },
            { "mzv", "application/metastream" }, { "nar", "application/zip" },
            { "nbmp", "image/nbmp" }, { "nc", "application/x-netcdf" },
            { "ndb", "x-lml/x-ndb" }, { "ndwn", "application/ndwn" },
            { "nif", "application/x-nif" }, { "nmz", "application/x-scream" },
            { "nokia-op-logo", "image/vnd.nok-oplogo-color" },
            { "npx", "application/x-netfpx" }, { "nsnd", "audio/nsnd" },
            { "nva", "application/x-neva1" }, { "oda", "application/oda" },
            { "oom", "application/x-AtlasMate-Plugin" },
            { "pac", "audio/x-pac" }, { "pae", "audio/x-epac" },
            { "pan", "application/x-pan" },
            { "pbm", "image/x-portable-bitmap" }, { "pcx", "image/x-pcx" },
            { "pda", "image/x-pda" }, { "pdb", "chemical/x-pdb" },
            { "pdf", "application/pdf" }, { "pfr", "application/font-tdpfr" },
            { "pgm", "image/x-portable-graymap" }, { "pict", "image/x-pict" },
            { "pm", "application/x-perl" }, { "pmd", "application/x-pmd" },
            { "png", "image/png" }, { "pnm", "image/x-portable-anymap" },
            { "pnz", "image/png" }, { "pot", "application/vnd.ms-powerpoint" },
            { "ppm", "image/x-portable-pixmap" },
            { "pps", "application/vnd.ms-powerpoint" },
            { "ppt", "application/vnd.ms-powerpoint" },
            { "pqf", "application/x-cprplayer" },
            { "pqi", "application/cprplayer" }, { "prc", "application/x-prc" },
            { "proxy", "application/x-ns-proxy-autoconfig" },
            { "ps", "application/postscript" },
            { "ptlk", "application/listenup" },
            { "pub", "application/x-mspublisher" },
            { "pvx", "video/x-pv-pvx" }, { "qcp", "audio/vnd.qcelp" },
            { "qt", "video/quicktime" }, { "qti", "image/x-quicktime" },
            { "qtif", "image/x-quicktime" },
            { "r3t", "text/vnd.rn-realtext3d" },
            { "ra", "audio/x-pn-realaudio" },
            { "ram", "audio/x-pn-realaudio" },
            { "rar", "application/x-rar-compressed" },
            { "ras", "image/x-cmu-raster" }, { "rdf", "application/rdf+xml" },
            { "rf", "image/vnd.rn-realflash" }, { "rgb", "image/x-rgb" },
            { "rlf", "application/x-richlink" },
            { "rm", "audio/x-pn-realaudio" }, { "rmf", "audio/x-rmf" },
            { "rmm", "audio/x-pn-realaudio" },
            { "rmvb", "audio/x-pn-realaudio" },
            { "rnx", "application/vnd.rn-realplayer" },
            { "roff", "application/x-troff" },
            { "rp", "image/vnd.rn-realpix" },
            { "rpm", "audio/x-pn-realaudio-plugin" },
            { "rt", "text/vnd.rn-realtext" }, { "rte", "x-lml/x-gps" },
            { "rtf", "application/rtf" }, { "rtg", "application/metastream" },
            { "rtx", "text/richtext" }, { "rv", "video/vnd.rn-realvideo" },
            { "rwc", "application/x-rogerwilco" }, { "s3m", "audio/x-mod" },
            { "s3z", "audio/x-mod" }, { "sca", "application/x-supercard" },
            { "scd", "application/x-msschedule" },
            { "sdf", "application/e-score" },
            { "sea", "application/x-stuffit" }, { "sgm", "text/x-sgml" },
            { "sgml", "text/x-sgml" }, { "sh", "application/x-sh" },
            { "shar", "application/x-shar" },
            { "shtml", "magnus-internal/parsed-html" },
            { "shw", "application/presentations" }, { "si6", "image/si6" },
            { "si7", "image/vnd.stiwap.sis" },
            { "si9", "image/vnd.lgtwap.sis" },
            { "sis", "application/vnd.symbian.install" },
            { "sit", "application/x-stuffit" },
            { "skd", "application/x-Koan" }, { "skm", "application/x-Koan" },
            { "skp", "application/x-Koan" }, { "skt", "application/x-Koan" },
            { "slc", "application/x-salsa" }, { "smd", "audio/x-smd" },
            { "smi", "application/smil" }, { "smil", "application/smil" },
            { "smp", "application/studiom" }, { "smz", "audio/x-smd" },
            { "snd", "audio/basic" }, { "spc", "text/x-speech" },
            { "spl", "application/futuresplash" },
            { "spr", "application/x-sprite" },
            { "sprite", "application/x-sprite" },
            { "spt", "application/x-spt" },
            { "src", "application/x-wais-source" },
            { "stk", "application/hyperstudio" }, { "stm", "audio/x-mod" },
            { "sv4cpio", "application/x-sv4cpio" },
            { "sv4crc", "application/x-sv4crc" }, { "svf", "image/vnd" },
            { "svg", "image/svg-xml" }, { "svh", "image/svh" },
            { "svr", "x-world/x-svr" },
            { "swf", "application/x-shockwave-flash" },
            { "swfl", "application/x-shockwave-flash" },
            { "t", "application/x-troff" },
            { "tad", "application/octet-stream" }, { "talk", "text/x-speech" },
            { "tar", "application/x-tar" }, { "taz", "application/x-tar" },
            { "tbp", "application/x-timbuktu" },
            { "tbt", "application/x-timbuktu" },
            { "tcl", "application/x-tcl" }, { "tex", "application/x-tex" },
            { "texi", "application/x-texinfo" },
            { "texinfo", "application/x-texinfo" },
            { "tgz", "application/x-tar" },
            { "thm", "application/vnd.eri.thm" }, { "tif", "image/tiff" },
            { "tiff", "image/tiff" }, { "tki", "application/x-tkined" },
            { "tkined", "application/x-tkined" }, { "toc", "application/toc" },
            { "toy", "image/toy" }, { "tr", "application/x-troff" },
            { "trk", "x-lml/x-gps" }, { "trm", "application/x-msterminal" },
            { "tsi", "audio/tsplayer" }, { "tsp", "application/dsptype" },
            { "tsv", "text/tab-separated-values" },
            { "tsv", "text/tab-separated-values" },
            { "ttf", "application/octet-stream" },
            { "ttz", "application/t-time" }, { "txt", "text/plain" },
            { "ult", "audio/x-mod" }, { "ustar", "application/x-ustar" },
            { "uu", "application/x-uuencode" },
            { "uue", "application/x-uuencode" },
            { "vcd", "application/x-cdlink" }, { "vcf", "text/x-vcard" },
            { "vdo", "video/vdo" }, { "vib", "audio/vib" },
            { "viv", "video/vivo" }, { "vivo", "video/vivo" },
            { "vmd", "application/vocaltec-media-desc" },
            { "vmf", "application/vocaltec-media-file" },
            { "vmi", "application/x-dreamcast-vms-info" },
            { "vms", "application/x-dreamcast-vms" },
            { "vox", "audio/voxware" }, { "vqe", "audio/x-twinvq-plugin" },
            { "vqf", "audio/x-twinvq" }, { "vql", "audio/x-twinvq" },
            { "vre", "x-world/x-vream" }, { "vrml", "x-world/x-vrml" },
            { "vrt", "x-world/x-vrt" }, { "vrw", "x-world/x-vream" },
            { "vts", "workbook/formulaone" }, { "wav", "audio/x-wav" },
            { "wax", "audio/x-ms-wax" }, { "wbmp", "image/vnd.wap.wbmp" },
            { "web", "application/vnd.xara" }, { "wi", "image/wavelet" },
            { "wis", "application/x-InstallShield" },
            { "wm", "video/x-ms-wm" }, { "wma", "audio/x-ms-wma" },
            { "wmd", "application/x-ms-wmd" },
            { "wmf", "application/x-msmetafile" },
            { "wml", "text/vnd.wap.wml" },
            { "wmlc", "application/vnd.wap.wmlc" },
            { "wmls", "text/vnd.wap.wmlscript" },
            { "wmlsc", "application/vnd.wap.wmlscriptc" },
            { "wmlscript", "text/vnd.wap.wmlscript" },
            { "wmv", "audio/x-ms-wmv" }, { "wmx", "video/x-ms-wmx" },
            { "wmz", "application/x-ms-wmz" }, { "wpng", "image/x-up-wpng" },
            { "wpt", "x-lml/x-gps" }, { "wri", "application/x-mswrite" },
            { "wrl", "x-world/x-vrml" }, { "wrz", "x-world/x-vrml" },
            { "ws", "text/vnd.wap.wmlscript" },
            { "wsc", "application/vnd.wap.wmlscriptc" },
            { "wv", "video/wavelet" }, { "wvx", "video/x-ms-wvx" },
            { "wxl", "application/x-wxl" }, { "x-gzip", "application/x-gzip" },
            { "xar", "application/vnd.xara" }, { "xbm", "image/x-xbitmap" },
            { "xdm", "application/x-xdma" }, { "xdma", "application/x-xdma" },
            { "xdw", "application/vnd.fujixerox.docuworks" },
            { "xht", "application/xhtml+xml" },
            { "xhtm", "application/xhtml+xml" },
            { "xhtml", "application/xhtml+xml" },
            { "xla", "application/vnd.ms-excel" },
            { "xlc", "application/vnd.ms-excel" },
            { "xll", "application/x-excel" },
            { "xlm", "application/vnd.ms-excel" },
            { "xls", "application/vnd.ms-excel" },
            { "xlt", "application/vnd.ms-excel" },
            { "xlw", "application/vnd.ms-excel" }, { "xm", "audio/x-mod" },
            { "xml", "text/xml" }, { "xmz", "audio/x-mod" },
            { "xpi", "application/x-xpinstall" }, { "xpm", "image/x-xpixmap" },
            { "xsit", "text/xml" }, { "xsl", "text/xml" },
            { "xul", "text/xul" }, { "xwd", "image/x-xwindowdump" },
            { "xyz", "chemical/x-pdb" }, { "yz1", "application/x-yz1" },
            { "z", "application/x-compress" },
            { "zac", "application/x-zaurus-zac" },
            { "zip", "application/zip	" }, };
    public static String toString(InputStream is, String charset) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, charset));
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                } else {
                    sb.append(line).append("\n");
                }
            }
            reader.close();
            is.close();
        } catch (IOException e) {
            Logger.d(e);
        }
        return sb.toString();
    }
}
