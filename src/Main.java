import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.Thread;
import java.net.Socket;
import java.util.Date;
/**
 * Created by zhangmin on 2014/12/23.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println(new Date().toLocaleString());
        //String fileName = args[0];
        //String ip = args[1];
        //String key = grgs[2];
        String ip = "192.168.202.81";
        String key = "C1E208B1FC38C4552BA5AF9B3C43DD7EF28F7CB5E1857FB2AD79BCAE989C581DA58D370D09B30BA5C9CA53709AA1FC2F88C08AB9FFC06090C45E82EE47E4873F13648A121C368F4F20176A0BA77F2BDCF28F7CB5E1857FB2AD79BCAE989C581DEFC81DC41F7B3E96ED30DE1E90D677C4F28F7CB5E1857FB2AD79BCAE989C581D21E9EBEA8D30595722E28F38E107B0B3FDE5B3C69CBCD5BA2CDEB055C1E37538C9B77743F74DD49DEC884F111C53B4AC21E9EBEA8D30595722E28F38E107B0B376160F437BD116F731FF70DDEEC8BB7B";
        FileManager fileManager = new FileManager("f://github.mp4");


        MyClient client = new MyClient(fileManager, ip, key, 4);
        //getInfo(fileManager.getFileSize(), ip, key);

        for(int i=0; i<1; i++)
        {
            new Thread(client).start();
            //Thread.sleep(1000);
        }
    }

    public static void getInfo(long fSize,String ip, String key)
    {
        Socket s =null;
        BufferedReader in = null;
        OutputStream outStream = null;
        BufferedOutputStream out = null;
        byte[] data = new byte[0];
        byte[] dataToSend =null;
        try {
            s =new Socket(ip, 8888);

            in = new BufferedReader(new InputStreamReader(s.getInputStream()));

            outStream = s.getOutputStream();
            out = new BufferedOutputStream(outStream);
            data = new String("POST /upload/?key="+key+" HTTP/1.1"+"\r\n").getBytes();
            data = ByteUtils.bytesMerger(data, new String("Content-Range: bytes */" + fSize + "\r\n").getBytes());
            data = ByteUtils.bytesMerger(data, new String("Content-Length: 0" + "\r\n").getBytes());
            data = ByteUtils.bytesMerger(data, new String("Connection: close" + "\r\n").getBytes());
            data = ByteUtils.bytesMerger(data, "\r\n".getBytes());

            out.write(data);
            out.flush();
            //s.shutdownOutput();
            System.out.println("------------------------------------------------");
            System.out.println("send:\n"+new String(data));
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

}
