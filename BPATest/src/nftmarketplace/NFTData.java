package nftmarketplace;

/////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////
/*****************NFTData*****************************/
/*Student will only get the .class files 
for the following object classes */
/////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////
class NFTData 
{
	private String userName;
	private String productName;
	private String rarity;
	private String collectionName;
	private double floorPrice;
	private String tokenType;
	private String nftAddress = null;  


	public WalletAddress walletAddress;

	public NFTData (String un, String co)
	{
		this.userName = un;
		this.productName = null;
		this.rarity = null;
		this.collectionName = co;
		this.tokenType = null;
		this.floorPrice = 0;
		this.walletAddress = new WalletAddress();

	}
	//LONG VERSION NOT USED IN TEST
	public NFTData (String un, String pn, String ra, 
			String co, String bc, Double fp)
	{
		this.userName = un;
		this.productName = pn;
		this.rarity = ra;
		this.collectionName = co;
		this.tokenType = bc;
		this.floorPrice = fp;
		this.walletAddress = new WalletAddress();
	}

	public String getUN()  //gets username
	{
		return this.userName;
	}

	public void setUN(String un)  //sets username
	{
		this.userName = un;
	}

	public String getPN()  //gets productname
	{
		return this.productName;
	}

	public void setPN(String pn)  //sets productname
	{
		this.productName = pn;
	}

	public String getRarity()  //gets the rarity
	{
		return this.rarity;
	}

	public void setRarity(String ra)  //sets rarity
	{
		this.rarity = ra;
	}

	public String getCollection()  //gets collection name
	{
		return this.collectionName;
	}

	public void setCollection(String co) //sets collection name
	{
		this.collectionName = co;
	}

	public Double getFloorPrice() //gets floorPrice
	{
		return this.floorPrice;   
	}

	public void setFloorPrice(Double f) //sets floorPrice amount
	{
		this.floorPrice = f;   
	}

	public String getTokenType()  //gets plate #
	{
		return this.tokenType;  
	}

	public void setTokenType(String bc)  //sets plate #
	{
		this.tokenType = bc;
	}

	public void setNFTAddress(String a)
	{

		nftAddress = a;
	}

	public String getNFTAddress()
	{
		return nftAddress;

	}


	public String toString() //not used; wrong result
	{
		return this.getClass().getName() + "WRONG " + " " + this.getUN() + "\n WRONG" + 
				this.getRarity() + " " + this.getCollection() +
				"\n WRONG" + this.getTokenType() + "\n WRONG" + this.getFloorPrice() + "]";
	}
} 


