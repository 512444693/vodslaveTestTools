import java.io.*;
import java.net.*;
public class MyServer {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        ServerSocket ss = null;
        Socket s = null;
        BufferedInputStream in = null;
        PrintWriter out = null;
        byte[] data = new byte[1024*1024*4];
        BufferedOutputStream file = null;
        try {
            ss = new ServerSocket(8189);

            file = new BufferedOutputStream(new FileOutputStream("d:\\1234.txt"));

            while(true){
                s = ss.accept();

                in = new BufferedInputStream(s.getInputStream());

                out = new PrintWriter(s.getOutputStream(),true);

                int len = 0;
                while((len = in.read(data)) != -1)
                {
                    //System.out.println("len = "+len);
                    //System.out.print(new String(data,0,len));
                    file.write(data,0,len);
                    file.flush();
                }
                file.write("\r\n----------------------------\r\n".getBytes());
                file.flush();
                s.shutdownInput();

                out.println("----------------------------");

                s.shutdownOutput();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        finally{
            try {
                if(file != null)
                    file.close();
                if(out != null)
                    out.close();
                if(in != null)
                    in.close();
                if(s != null)
                    s.close();
                if(ss != null)
                    ss.close();
            } catch (Exception e2) {
                // TODO: handle exception
            }

        }
    }

}
