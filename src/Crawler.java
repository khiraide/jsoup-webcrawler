import java.net.*;
import java.util.Vector;
import java.io.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {
	
	public static final int MEMBER_TOTAL = 334;
	private static final boolean Element = false;
	
	public static String getHTML(URL url) throws Exception {

	      String lines = null;
	      try {       
	        // Instantiate CookieManager;
	        // make sure to set CookiePolicy
	        CookieManager manager = new CookieManager();
	        manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
	        CookieHandler.setDefault(manager);
	        CookieStore cookieJar =  manager.getCookieStore();
	        HttpCookie cookie = new HttpCookie("SMFCookie138", "a%3A4%3A%7Bi%3A0%3Bs%3A1%3A%226%22%3Bi%3A1%3Bs%3A40%3A%22edc646b861e10aef1fc90b1b9651250ee3c94108%22%3Bi%3A2%3Bi%3A1539142855%3Bi%3A3%3Bi%3A0%3B%7D");

	        // get content from URLConnection;
	        // cookies are set by web site
	        cookieJar.add(url.toURI(), cookie);
	        //URLConnection connection = url.openConnection();
	        //connection.getContent();
	        BufferedReader in = new BufferedReader(
	            new InputStreamReader(url.openStream()));

	            String inputLine;
	            while ((inputLine = in.readLine()) != null)
	                //System.out.println(inputLine);
	                lines += inputLine + "\n";
	            in.close();
	    } catch(Exception e) {
	        System.out.println("Unable to get cookie using CookieHandler");
	        e.printStackTrace();
	    }
	    return lines;
	    }
	
	public static void makeFile() throws Exception {
		PrintWriter out; //= new PrintWriter("user.txt");
		URL url = null;
		String html = "";
		String user = "";
		String dateString = "";
		int page = 0;
		int totalPage = 0;
		int uCount = 0;
		int uVar = 0;
		boolean flag = false;
		int[] userList;
		userList = new int[20];
		userList[0] = 1;
		userList[1] = 17;
		userList[2] = 144;
		userList[3] = 1473;
		userList[4] = 37;
		userList[5] = 1453;
		userList[6] = 1422;
		userList[7] = 5;
		userList[8] = 89;
		userList[9] = 67;
		userList[10] = 14;
		userList[11] = 383;
		userList[12] = 36;
		userList[13] = 30;
		userList[14] = 226;
		userList[15] = 118;
		userList[16] = 1693;
		userList[17] = 9;
		userList[18] = 175;
		userList[19] = 1594;
		
		
		try { 
			  for (int i=19; i < 20; i++) {
				  out = new PrintWriter("user" + uVar + ".txt");
				  page = 0;
				  totalPage = 0;
				  flag = false;
				  
				  while(page == 0 || page < totalPage) {
					  Thread.currentThread().sleep(1000);
				      url = new URL("http://www.hawaiigamers.net/forums/index.php?action=profile;u=" + Integer.toString(userList[i]) + ";area=showposts;start=" + Integer.toString(page));
				      html = getHTML(url);
				      Document doc = Jsoup.parse(html);
				      Elements media = doc.select(".topic strong a:eq(1)");
				      Elements media2 = doc.select(".topic strong a:eq(0)");
				      Elements title = doc.select("title:matches(\\b(Show Posts - )([\\D\\w]+)\\b)");
				      Elements date = doc.select(".smalltext");
				      Elements pages = null;
				      for (int n=4; n >= 0; n--) {
				    	  pages = doc.select(".pagesection[style^=margin] span a:eq(" + n + ")"); 
				    	  if (!pages.text().equals("")) {
				    		  break;
				    	  }
				     }
				      
				      if (pages.text().equals("")) {
				    	  pages = doc.select(".pagesection[style^=margin] span strong"); 
				      }				      
				      if (!title.text().equals("")) { //If the page exists, grab the data.
				    	  System.out.println(title.text());
				    	  System.out.println(pages.text());
				    	  System.out.println(page);
					      if (flag == false) {
					    	  System.out.println(pages.text());
					    	  totalPage = Integer.parseInt(pages.text()) * 15;
						      user = title.text().substring(13);
					    	  out.println(user);
					    	  flag = true;
					      }
					      //for (Element src : media) {
					    //	  out.println(src.text());
					      //}
					      
					      System.out.println("media "+ media.size());
					      System.out.println("media "+ media2.size());
					      System.out.println("date "+ date.size());
					     // String pattern = "(?i)(<title.*?>)(.+?)(</title>)";
					      String pattern = "(« on: )(.+)(»)";
					    
					      for (int b=0; b < media.size(); b++) {
					    	  out.println(media2.get(b).text());
					    	  out.println(media.get(b).text());
					    	  String updated = date.get(b).text().replaceAll(pattern, "$2");
					    	  out.println(updated);
					      }
				      }
				      else {
				    	  System.out.println("No Page Exists bro!");
				    	  break;
				      }
				      page = page + 15;
				      html = "";
				      user = "";
				  }
				  
				  if (page+15 >= totalPage && totalPage != 0) {
			    	  out.close();
			    	  uVar++;
			      }
			  }
			
		} catch(Exception e) {
	        System.out.println("Error");
	        e.printStackTrace();
	    }
	}
  
    public static void main(String[] args) throws Exception {

    	makeFile();
      
    }
    
}