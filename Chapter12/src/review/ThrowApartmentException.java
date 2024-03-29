/*
 * Foster Smith
 * 09.02.22
 * ThrowApartmentException.java
 * CH13 #6
 */

package review;

public class ThrowApartmentException {

	public static void main(String[] args) {
		
		int numApartments = 20;
		
		String[] addresses = {"Gesällvägen 3, 863 41 Sundsvall, Sweden",
				"2110 W Ikea Way, Tempe, AZ 85284",
				"6500 IKEA Way, Las Vegas, NV 89148",
				"586 Chem. de Touraine, Boucherville, QC J4B 5E4, Canada",
				"Via dell'Artigianato, 7, 20061 Carugate MI, Italy",
				"Krebsen 40, 9200 Aalborg, Denmark"};
		String[] apptNums = {"534","2","232","0987654321","685","759"};
		int[] bedrooms = {0, 3, 2, 4, 900, 1};
		int[] rents = {615, 512, 2000, 12, 5465652, 782};
		
		Apartment[] apartments = new Apartment[6];
		
		for(int i = 0; i < apartments.length; ++i) {
			try {
				apartments[i] = new Apartment(addresses[i], apptNums[i], bedrooms[i], rents[i]);
				System.out.println("Successfully created apartment with address "+addresses[i]);
			} catch(ApartmentException e) {
				System.out.println(e.getMessage());
			}
		}
				
	}
	
}
