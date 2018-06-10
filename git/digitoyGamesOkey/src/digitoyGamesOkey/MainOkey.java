package digitoyGamesOkey;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;

public class MainOkey
{
	static HashMap<Integer, String> okeyStone 		  		= new HashMap<>();
	static LinkedHashMap<Integer, String> shuffledOkeyStone = new LinkedHashMap<>();
	static HashMap<Integer, String> okeyNumber 		  		= new HashMap<>();
	static Integer[] lastPuan 								= new Integer[4];
	
	public static void main(String[] args)
	{
		arrangeOkeyStone();
		okeyTaslarýYazdýr(okeyStone);
		shuffleOkeyStone();
		okeyTaslarýYazdýr(shuffledOkeyStone);
		getOkeyNumber();
		okeyTaslarýYazdýr(shuffledOkeyStone);
		tasDagit();
		findTheBestPlayer();
		siralama();
	}
	
	public static void arrangeOkeyStone()
	{
		String[] stoneColor = {"sarý","mavi","siyah","kýrmýzý"};
		int keyCount 		= 0;
		
		while (keyCount != 106)
		{
			for(int i=0; i<stoneColor.length; i++)
			{
				for (int j=1; j<=13; j++)
				{
					String value = stoneColor[i]+j;
					keyCount++;
					okeyStone.put(keyCount, value);
				}
			}
			keyCount++;
			okeyStone.put(keyCount, "sahteOkey");
			System.out.println(keyCount);
		}
	}
	
	public static void shuffleOkeyStone()
	{
		int count = 0;
		List keys = new ArrayList(okeyStone.keySet());
		Collections.shuffle(keys);
		for (Object o : keys)
		{
			shuffledOkeyStone.put((Integer) o, okeyStone.get(o));
			count++;
			System.out.println(count);
		}
	}
	
	public static void getOkeyNumber()
	{
		int gostergeNum = 0;
		int okeyKeyNum	= 0;
		while(gostergeNum == 0 || (shuffledOkeyStone.get(gostergeNum).contains("13")) || (shuffledOkeyStone.get(gostergeNum).contains("sahteOkey")))
		{
			Random r 	= new Random();
			gostergeNum = r.nextInt(106);
		}
		
		System.out.println("Gösterge: " + gostergeNum 	  + " - " + shuffledOkeyStone.get(gostergeNum));
		System.out.println("Okey: " 	+ (gostergeNum+1) + " - " + shuffledOkeyStone.get(gostergeNum+1));
		okeyKeyNum = gostergeNum+1;
		String okeyValue  = shuffledOkeyStone.get(okeyKeyNum);
		Set entrySet 	  = shuffledOkeyStone.entrySet();
		Iterator iterator = entrySet.iterator();
		while(iterator.hasNext())
	    {
	       Map.Entry okeyTaslari = (Map.Entry)iterator.next();
	       if(shuffledOkeyStone.get(okeyTaslari.getKey()).equals(okeyValue))
	       {
	    	   shuffledOkeyStone.replace((Integer) okeyTaslari.getKey(), shuffledOkeyStone.get(okeyTaslari.getKey()), "Okey");
	       }
	       else if(shuffledOkeyStone.get(okeyTaslari.getKey()).equals("sahteOkey"))
	       {
	    	   shuffledOkeyStone.replace((Integer) okeyTaslari.getKey(), "sahteOkey", okeyValue);
	       }
	    }
	}
	
	static HashMap[] oyuncu = new HashMap[4];
	
	public static void tasDagit()
	{
		for (int player=0; player<4; player++)
		{
			oyuncu[player] = new HashMap<Integer, String>();
		}
		
		Random r 		= new Random();
		int firstPlayer = r.nextInt(3);
		
		for(int i=0; i<4; i++)
		{
			int kullanilanTaslar[];
			int maxStone = 14;
			if(firstPlayer == i)
			{
				maxStone = 15;
			}
			
			if(maxStone == 14)
			{
				kullanilanTaslar = new int[14];
			}
			else
			{
				kullanilanTaslar = new int[15];
			}
			
			int usedStone     = 0;
			Set entrySet 	  = shuffledOkeyStone.entrySet();
			Iterator iterator = entrySet.iterator();
			while(iterator.hasNext() && maxStone>0)
		    {
		       Map.Entry okeyTaslari = (Map.Entry)iterator.next();
		       oyuncu[i].put(okeyTaslari.getKey(), okeyTaslari.getValue());
		       kullanilanTaslar[usedStone] = (Integer) okeyTaslari.getKey();
		       usedStone++;
		       maxStone--;
		    }
			
			System.out.println("Oyuncu" + i);
			Set entrySetOyuncu 	    = oyuncu[i].entrySet();
			Iterator iteratorOyuncu = entrySetOyuncu.iterator();
			while(iteratorOyuncu.hasNext())
		    {
		       Map.Entry okeyTaslariOyuncu = (Map.Entry)iteratorOyuncu.next();
		       System.out.println(okeyTaslariOyuncu.getKey() + "-" + okeyTaslariOyuncu.getValue());
		    }
			System.out.println("----");
			
			for(int cikarilacakTas=0; cikarilacakTas<kullanilanTaslar.length; cikarilacakTas++)
			{
				shuffledOkeyStone.remove(kullanilanTaslar[cikarilacakTas]);
			}
		}
	}
	
	public static void findTheBestPlayer()
	{
		for(int i=0; i<4;i++)
		{
			ArrayList<Integer> sari    = new ArrayList<>();
			ArrayList<Integer> siyah   = new ArrayList<>();
			ArrayList<Integer> mavi    = new ArrayList<>();
			ArrayList<Integer> kirmizi = new ArrayList<>();
			int okeyVarMi		   	   = 0;
			
			System.out.println("Oyuncu" + i);
			Set entrySetOyuncu 	    = oyuncu[i].entrySet();
			Iterator iteratorOyuncu = entrySetOyuncu.iterator();
			while(iteratorOyuncu.hasNext())
		    {
		       Map.Entry okeyTaslariOyuncu = (Map.Entry)iteratorOyuncu.next();
		       if(okeyTaslariOyuncu.getValue().toString().contains("sarý"))
		       {
		    	   sari.add((Integer) okeyTaslariOyuncu.getKey());
		       }
		       else if(okeyTaslariOyuncu.getValue().toString().contains("siyah"))
		       {
		    	   siyah.add((Integer) okeyTaslariOyuncu.getKey());
		       }
		       else if(okeyTaslariOyuncu.getValue().toString().contains("mavi"))
		       {
		    	   mavi.add((Integer) okeyTaslariOyuncu.getKey());
		       }
		       else if(okeyTaslariOyuncu.getValue().toString().contains("kýrmýzý"))
		       {
		    	   kirmizi.add((Integer) okeyTaslariOyuncu.getKey());
		       }
		       else if(okeyTaslariOyuncu.getValue().toString().contains("Okey"))
		       {
		    	   okeyVarMi++;
		       }
		       System.out.println(okeyTaslariOyuncu.getKey() + "-" + okeyTaslariOyuncu.getValue());
		    }
			int sariPuan 	= evaluation(sari);
			int siyahPuan 	= evaluation(siyah);
			int maviPuan 	= evaluation(mavi);
			int kirmiziPuan = evaluation(kirmizi);
			
			lastPuan[i] = sariPuan+siyahPuan+maviPuan+kirmiziPuan;
			
			if(okeyVarMi == 1)
			{
				lastPuan[i] = lastPuan[i]-2;
			}
			else if(okeyVarMi == 2)
			{
				lastPuan[i] = lastPuan[i]-4;
			}
			
			System.out.println("----");
		}
	}
	
	public static int evaluation(ArrayList<Integer> alist)
	{
		int puan = 0, val1 = 0, val2 = 0, minus = 0;
		Collections.sort(alist);
		ListIterator<Integer> listIterator = alist.listIterator();
		while(listIterator.hasNext())
		{
			if(val1 == 0)
			{
				val1 = listIterator.next();
			}
			else
			{
				val2 = listIterator.next();
			}
			
			if(val2 == 0)
			{
				minus = 0;
			}
			else
			{
				minus = val2 - val1;
				val1  = val2;
			}
			puan += minus;
		}
		return puan;
	}
	
	public static void siralama()
	{
		int champion = 0;
		for(int i = 1; i<4; i++)
		{
			if(lastPuan[champion] > lastPuan[i])
			{
				champion = i;
			}
		}
		
		System.out.println("Kazanmaya Yakýn Oyuncu: Oyuncu"+ (champion) );
	}
	
	public static void okeyTaslarýYazdýr(HashMap<Integer, String> list)
	{
		int count = 0;
		Set<Entry<Integer, String>> entrySet = list.entrySet();
	    Iterator iterator 					 = entrySet.iterator();
	    
	    while(iterator.hasNext())
	    {
	    	count++;
	    	Map.Entry okeyTaslari = (Map.Entry)iterator.next();
	    	System.out.println(count + " Key is: "+okeyTaslari.getKey() + " & " + " value is: "+okeyTaslari.getValue());
	   }
	}
}