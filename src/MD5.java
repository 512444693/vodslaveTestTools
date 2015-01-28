/**
 * Created by zhangmin on 2014/12/23.
 */
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
//import org.apache.commons.codec.binary.Base64;
public class MD5 {

    /**
     * @param args
     * @throws java.security.NoSuchAlgorithmException
     */
    public static byte[] getMD5(byte[] data){
        // TODO Auto-generated method stub
        MessageDigest messageDigest;
        byte[] resultByteArray = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(data);
            resultByteArray = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return resultByteArray;
    }

    public static String byteArrayToHex(byte[] byteArray) {

        // 首先初始化一个字符数组，用来存放每个16进制字符
        char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F' };
        // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
        char[] resultCharArray = new char[byteArray.length * 2];
        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }
        // 字符数组组合成字符串返回
        return new String(resultCharArray);
    }
   /*
    public static byte[] decode(final byte[] bytes) {
        return Base64.decodeBase64(bytes);
    }


    public static String encode(final byte[] bytes) {
        return new String(Base64.encodeBase64(bytes));
    }
    public static void main(String[] args)
    {
        byte[] d = null;
        d = ByteUtils.hex2Bytes("CF0DD777E2CFCA565BAEA38DB05F3582D0EA06A6504F6E77AC138CBE667D3471C522020CF17869A34F107F4C57DA6FE67CD4A902E9FB86958425EAFB4C1769822E4BA7E30EE4607E2B67C49903F8B9E8B0B0329632AA6669A279A265833B215F68FB4D4121E21FA4713242FFCE292236C5025E635FBF95700B98B698E4417462B90A68C1AE5CE1DEBB54EB07DCF4BD55B8100401EC642A3B4B8CF19E7B473A53");
        System.out.println(encode(d));
    }
*/

}
