package nftmarketplace;

////////////////////////////////////////////////////
////////////////////////////////////////////////////
/*****************WalletAddress********************/
/*Student will only get the .class files for this */
////////////////////////////////////////////////////
///////////////////////////////////////////////////

class WalletAddress 
{
	private String publicKeyWallet;

	public WalletAddress()
	{
		this.publicKeyWallet = "NA";
	}     

	public WalletAddress(String a)
	{
		this.publicKeyWallet = a; 

	}
	public String getAdd()
	{
		return this.publicKeyWallet;
	}   

	public void setAdd(String s)
	{
		publicKeyWallet = s;

	}    
}  

