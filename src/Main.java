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
        String ip = "192.168.200.13";
        String key = "D4C22620C037C093791EDD27033DC2D50C49893F8F81FC6A9D5B9A622C506127B35705F8091017004A074E053879E650E10F0E25D6A92E83A330FACE9B3CB332085D9D22EA307DCFC699A9F5BB37A3F52E5EC0721C43C640DF563A627804012DD38A8AD0A8DD671EB2219A6B05831D21B73283C463CA593AF1010C4151C9AA0770D6EFC95A7F98B15AFE7DA2514550EFD6DEB860FC7D80D05720C03321D600F3";
        FileManager fileManager = new FileManager("D://video11.mp4");


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
