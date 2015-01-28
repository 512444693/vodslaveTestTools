/**
 * Created by zhangmin on 2014/12/23.
 */
import java.io.*;
import java.net.*;
import java.util.Date;

public class MyClient implements Runnable {

    /**
     * @param args
     * @throws java.io.IOException
     * @throws InterruptedException
     */


    public MyClient(FileManager fileManager, String ip, String key, int blockSize){
        this.fileManager = fileManager;
        this.ip = ip;
        this.key = key;
        this.blockSize = blockSize;
        data = new byte[1024*1024*blockSize];
    }

    public void run() {
/*
        do{

            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            fileManager.getData(this);
            if(dataReadLength != -1){
                contentMD5 = MD5.byteArrayToHex(MD5.getMD5(subBytes(data, 0, dataReadLength)));
                sendPostByTcp(subBytes(data, 0, dataReadLength), position+"-"+(position+dataReadLength-1)+"/"+fileSize,dataReadLength,contentMD5);
                System.out.println("Thread "+Thread.currentThread().getId()+" send "+position+"-"+(position+dataReadLength-1)+"/"+fileSize);
                System.out.println(new Date().toLocaleString());
            }
        }
        while(dataReadLength != -1);
*/
        //fileManager.getData(this);
        fileManager.getData(this);
        fileManager.getData(this);
        if(dataReadLength != -1){
            contentMD5 = MD5.byteArrayToHex(MD5.getMD5(subBytes(data, 0, dataReadLength)));
            sendPostByTcp(subBytes(data, 0, dataReadLength), position+"-"+(position+dataReadLength-1)+"/"+fileSize,dataReadLength,contentMD5);
            System.out.println("Thread "+Thread.currentThread().getId()+" send "+position+"-"+(position+dataReadLength-1)+"/"+fileSize);

        }

        //System.out.println(System.currentTimeMillis());
    }
    public void sendPostByTcp(byte[] data, String contentRange, long contentLength, String contentMD5)
    {
        Socket s =null;
        BufferedReader in = null;
        OutputStream outStream = null;
        BufferedOutputStream out = null;
        byte[] dataToSend =null;
        try {
            s =new Socket(ip, 8888);
            //s =new Socket("192.168.200.13", 8888);
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));

            outStream = s.getOutputStream();
            out = new BufferedOutputStream(outStream);
            //dataToSend = "".getBytes();

            dataToSend = new String("POST /upload/?key="+key+" HTTP/1.1"+"\r\n").getBytes();
            dataToSend = plusBytes(dataToSend, new String("Content-Length: "+contentLength+"\r\n").getBytes());
            dataToSend = plusBytes(dataToSend, new String("Content-Range: bytes "+contentRange+"\r\n").getBytes());
            dataToSend = plusBytes(dataToSend, new String("Content-Type: application/octet-stream"+"\r\n").getBytes());
            dataToSend = plusBytes(dataToSend, new String("Content-MD5: "+contentMD5+"\r\n").getBytes());
            //dataToSend = plusBytes(dataToSend, new String("Origin: http://192.168.202.81:8888"+"\r\n").getBytes());
            dataToSend = plusBytes(dataToSend, new String("Access-Control-Request-Method: POST"+"\r\n").getBytes());
            dataToSend = plusBytes(dataToSend, new String("Connection: close"+"\r\n").getBytes());
            dataToSend = plusBytes(dataToSend,"\r\n".getBytes());

            dataToSend = plusBytes(dataToSend,data);
            //byte[] t = "12".getBytes();
            dataToSend = ByteUtils.subBytes(dataToSend,0,dataToSend.length-5);
            out.write(dataToSend);
            out.flush();
            //s.shutdownOutput();
            System.out.println("------------------------------------------------");
            System.out.println("send:\n"+new String(ByteUtils.subBytes(dataToSend,0,1024)));
            System.out.println("recv:");
            String line = "";
            while((line=in.readLine()) != null)
            {
                System.out.println(line);
            }
            System.out.println("------------------------------------------------");
            s.shutdownInput();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        finally{
            try {
                if(outStream != null)
                    outStream.close();
                if(out != null)
                    out.close();
                if(in != null)
                    in.close();
                if(s != null)
                    s.close();
            } catch (Exception e2) {
                // TODO: handle exception
            }
        }
    }
    public byte[] subBytes(byte[] src, int begin, int count) {
        byte[] bs = new byte[count];
        for (int i=begin; i<begin+count; i++) bs[i-begin] = src[i];
        return bs;
    }
    public byte[] plusBytes(byte[] arg1, byte[] arg2)
    {
        byte[] ret = new byte[arg1.length+arg2.length];
        int position = 0;
        for(int i=0; i<arg1.length; i++)
            ret[position++] = arg1[i];
        for(int i=0; i<arg2.length; i++)
            ret[position++] = arg2[i];
        return ret;
    }
    public long position = 0;
    public int dataReadLength = 0;
    public byte[] data = null;
    public long fileSize = 0;
    public String contentMD5 = "";
    public FileManager fileManager = null;
    public String ip = "";
    public String key = "";
    public int blockSize = 0;

}
