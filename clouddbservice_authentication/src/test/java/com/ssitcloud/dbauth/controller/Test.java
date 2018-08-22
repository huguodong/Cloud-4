package com.ssitcloud.dbauth.controller;

import com.ssitcloud.dbauth.utils.RsaHelper;



/**
 * <p>2016年4月9日 上午10:28:56
 * @author hjc
 *
 */
public class Test {
	public static void main(String[] args)  {
		String publicKey = "<RSAKeyValue><Modulus>ALIXPJ3UkxV/kCndHTj+DFKuXOlIdUMdax4SOqtIKo0gT6Gfe0abdWtu1mMyHamKw/MmHMvDHU/mzwkGNKtYJjcJr91T0jFg6fJ/Gln2ah5qty3KXqtD+SNc8a7ghWmcUeoDOSWGzh9KWaSUhG3fcZkSesPr86Bw62EvsfaUVNOR</Modulus><Exponent>AQAB</Exponent></RSAKeyValue>";
		String privateKry = "<RSAKeyValue><Modulus>ALIXPJ3UkxV/kCndHTj+DFKuXOlIdUMdax4SOqtIKo0gT6Gfe0abdWtu1mMyHamKw/MmHMvDHU/mzwkGNKtYJjcJr91T0jFg6fJ/Gln2ah5qty3KXqtD+SNc8a7ghWmcUeoDOSWGzh9KWaSUhG3fcZkSesPr86Bw62EvsfaUVNOR</Modulus><Exponent>AQAB</Exponent><P>ANgGzRDJR0rUI4zARACpOp35RZdv5d8IvPZ7ArSoCvAeVEsKWqIdJBuvjOwev7+k6smKPbSVOceLDqZfvnr0REk=</P><Q>ANMLadSWwSTCj+dnI+3uSrabs27ffdQt6AvITub5wbYZ5HWYHrG8aDsIMqtLl7bLwaJb+ZESGoyPBblQDTchBQk=</Q><DP>DqlngZwnmoyLXSIve1wA/nfMoVqW32xYZuIybNB67ZEhZ3ZscFRJ/xcLGXt8yCUJSmR3i6oVIdXuSJx28SFjAQ==</DP><DQ>APtvnaap9XLWWpxXRXczb9AfsKdnnYItL0jaXSbSaPeL4aQ4mFkpHwU1vyhV52rhVtEYwz0TVI4h16/wr83+iQ==</DQ><InverseQ>B8G9tRvIHXnNHC94zIgSZsksWNbfVeYLJNFrRrN3jZFXApKd6zraxgEwWVLFEhzdicU/F3ErBMw6GzWI6bD1FQ==</InverseQ><D>NVybEBui2r03QCP93pbYsGmIc2n/oNWOxBroM2xTO6gj1CTKNlTccQ1r9ZsNokBvCEsyM6fJ/6gD/ws5+uFVyYYJLNZJJJVywSARvY20f6RPKYpZnbL1ZBDd0BqVLPHcru1p4jz+ZY3Uw51HFNHvsfkT57TohaHY4PAuxhS2v0E=</D></RSAKeyValue>";
		String enCode = RsaHelper.encryRSA("123456", publicKey);
		System.out.println(enCode);
		
		String deCode = RsaHelper.decryRSA(enCode, privateKry);
		
		System.out.println(deCode);
		
		
	}
}
