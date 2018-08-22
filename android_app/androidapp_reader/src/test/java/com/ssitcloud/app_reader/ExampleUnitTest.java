package com.ssitcloud.app_reader;

import com.ssitcloud.app_reader.common.entity.HttpResponce;
import com.ssitcloud.app_reader.common.utils.Base64Helper;
import com.ssitcloud.app_reader.common.utils.HttpClientUtil;
import com.ssitcloud.app_reader.common.utils.RsaHelper;

import org.junit.Test;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void testRSAUtil(){

            KeyPair kp = RsaHelper.generateRSAKeyPair(512);
            PublicKey pubKey = kp.getPublic();
            PrivateKey priKey = kp.getPrivate();

            String pubKeyXml = RsaHelper.encodePublicKeyToXml(pubKey);
            String priKeyXml = RsaHelper.encodePrivateKeyToXml(priKey);
            System.out.println("====公钥====");
            System.out.println(pubKeyXml);
            System.out.println("====私钥====");
            System.out.println(priKeyXml);

            PublicKey pubKey2 = RsaHelper.decodePublicKeyFromXml(pubKeyXml);
            PrivateKey priKey2 = RsaHelper.decodePrivateKeyFromXml(priKeyXml);

            System.out.println("====公钥对比====");
            System.out.println(pubKey.toString());
            System.out.println("------");
            System.out.println(pubKey2.toString());

            System.out.println("====私钥对比====");
            System.out.println(priKey.toString());
            System.out.println("------");
            System.out.println(priKey2.toString());

            try {
                String pubKeyXml3 = "<RSAKeyValue><Modulus>rHESyuI3ny4MLsqDBalW9ySaodCL0e6Bsrl01Q5G1qm2wjUoGULazZSNqZY+JQNjU92tW3Snk5RPIkv+wDj+uOT9LTUjQImltHnzqMvbt06GipVXDOyBLTa7G/zRIe/CrjyJ+XEYX2xIhpe5ayowl3HHUpZ71jRNioyxaVVZ8S0=</Modulus><Exponent>AQAB</Exponent></RSAKeyValue>";
                String priKeyXml3 = "<RSAKeyValue><Modulus>rHESyuI3ny4MLsqDBalW9ySaodCL0e6Bsrl01Q5G1qm2wjUoGULazZSNqZY+JQNjU92tW3Snk5RPIkv+wDj+uOT9LTUjQImltHnzqMvbt06GipVXDOyBLTa7G/zRIe/CrjyJ+XEYX2xIhpe5ayowl3HHUpZ71jRNioyxaVVZ8S0=</Modulus><Exponent>AQAB</Exponent><P>5a7uM+IeY8QMVQl0q88ZTqWbB555l7+366cUIClTN8z2ZXzTnWFCNoQzUrG14FouJFYumFZD12Ni5MkJK6gqSw==</P><Q>wDMhwwO4kz82uSG+FlCBr06fYk2COTg0TofmSp/5OrVqgkBIe7FgpTpVGzGLk0mvOLcy6UZftq//W0Saow6nZw==</Q><DP>FbjDgliiMyE5YVlxlUYSyKNU1BWivj09caXte1UtL5vMubBiewHVtz4tdGamIr+kmX8lDPcrl1Uo5yY0HdLbnQ==</DP><DQ>kIjjJsgxkWnEOUyKqjU4kSDK8x3ehDEkBLpmEFBlGCU9R14YJAyr5RUM0zpbABQ1VK1P9+UYLUYE/hmFQIHQmQ==</DQ><InverseQ>pxQDThwSnUZ4EaNaCPl1ovYypdQUZaZ/Sld1+0n8FEjkmRcGP1R9VMuj1ViPZg3rvm2GeP8Xv1SJqJUVueWiGA==</InverseQ><D>DxBNoPWEAF7IZ6n/KhZx52MGMw6BuFQKdm9m+lml7Iik03BLUXGapYzNlzvtr9QM8D2UMEIPhX/WLdvPpEEWVzGnD7XpLXjGwfu1ZkJRcXPEZEZ2subh5ZBqOWCFWKv5WwgGYWuYDLHfrBlBgSFWR8cZuyqkmMsWl4CiadXqGA0=</D></RSAKeyValue>";

                System.out.println((new Date()).toLocaleString() + ": 加载公钥中。。。");
                PublicKey pubKey3 = RsaHelper.decodePublicKeyFromXml(pubKeyXml3);
                System.out.println((new Date()).toLocaleString() + ": 加载私钥中。。。");
                PrivateKey priKey3 = RsaHelper.decodePrivateKeyFromXml(priKeyXml3);

                String dataStr = "Java与.NET和平共处万岁！";
                byte[] dataByteArray = dataStr.getBytes("utf-8");
                System.out.println("data的Base64表示："
                        + Base64Helper.encode(dataByteArray));

                System.out.println((new Date()).toLocaleString() + ": 加密中。。。"); // 加密
                byte[] encryptedDataByteArray = RsaHelper.encryptData(
                        dataByteArray, pubKey3);

                System.out.println("encryptedData的Base64表示："
                        + Base64Helper.encode(encryptedDataByteArray));
                System.out.println((new Date()).toLocaleString() + ": 解密中。。。"); // 解密
                // byte[]
                byte[] decryptedDataByteArray = RsaHelper.decryptData(
                        encryptedDataByteArray, priKey3);
                System.out.println(new String(decryptedDataByteArray, "utf-8"));// 签名
                System.out.println((new Date()).toLocaleString() + ": 签名中。。。");
                byte[] signDataByteArray = RsaHelper.signData(dataByteArray,
                        priKey3);
                System.out.println("signData的Base64表示："
                        + Base64Helper.encode(signDataByteArray)); // 验签
                System.out.println((new Date()).toLocaleString() + ": 验签中。。。");
                boolean isMatch = RsaHelper.verifySign(dataByteArray,
                        signDataByteArray, pubKey3);
                System.out.println("验签结果：" + isMatch);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
    }
}