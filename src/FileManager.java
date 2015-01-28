import java.io.*;

/**
 * Created by zhangmin on 2014/12/23.
 */
public class FileManager {
    private long position;
    private int dataReadLength;

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    private long fileSize ;
    private File file = null;
    private FileInputStream fileInputStream = null;
    private BufferedInputStream in = null;
    public FileManager(String filePath)
    {
        file = new File(filePath);
        try {
            fileInputStream = new FileInputStream(file);
            in = new BufferedInputStream(fileInputStream);
            position = 0;
            dataReadLength = 0;
            fileSize = file.length();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 如果数据没有读完，返回dataReadLength大于0
     * 否则dataReadLength等于-1
     * @param client
     */
    public synchronized void getData(MyClient client)
    {
        if(position < fileSize)
        {
            try {
                if((dataReadLength = in.read(client.data)) != -1)
                {
                    client.dataReadLength = dataReadLength;
                    client.position = position;
                    client.fileSize = fileSize;
                    position += dataReadLength;
                }
                else
                {
                    System.out.println("close file");
                    client.dataReadLength = -1;
                    try {
                        if(in != null)
                            in.close();
                        if(fileInputStream != null)
                            fileInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            client.dataReadLength = -1;
        }
    }
    public void close()
    {
        try {
            if(in != null)
                in.close();
            if(fileInputStream != null)
                fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
