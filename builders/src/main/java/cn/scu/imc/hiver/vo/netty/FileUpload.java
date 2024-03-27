package cn.scu.imc.hiver.vo.netty;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 用来传输文件的类型
 */
public class FileUpload implements Serializable {

    private static final long serialVersionUID =231282L;
    //文件名
    private String fileName;
    //项目名
    private String projectName;
    //当前构建版本
    private long version;
    //文件总分块数
    private int total;
    //当前传输的分块索引
    private int index;
    //文件块的大小 默认为1024 byte
    private int length = 1024;
    //数据
    private byte[] bytes;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public String toString() {
        return "SendFile{" +
                ", fileName='" + fileName + '\'' +
                ", total=" + total +
                ", index=" + index +
                ", bytes=" + Arrays.toString(bytes) +
                '}';
    }
}
