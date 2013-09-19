/*
 * 2013-9-19:下午9:54:11
 * Keen
 *
 * {@link www.razdev.com}
 *
 * KKKKKKKKK    KKKKKKK                                                          
 * K:::::::K    K:::::K                                                          
 * K:::::::K    K:::::K                                                          
 * K:::::::K   K::::::K                                                          
 * KK::::::K  K:::::KKK    eeeeeeeeeeee        eeeeeeeeeeee    nnnn  nnnnnnnn    
 *   K:::::K K:::::K     ee::::::::::::ee    ee::::::::::::ee  n:::nn::::::::nn  
 *   K::::::K:::::K     e::::::eeeee:::::ee e::::::eeeee:::::een::::::::::::::nn 
 *   K:::::::::::K     e::::::e     e:::::ee::::::e     e:::::enn:::::::::::::::n
 *   K:::::::::::K     e:::::::eeeee::::::ee:::::::eeeee::::::e  n:::::nnnn:::::n
 *   K::::::K:::::K    e:::::::::::::::::e e:::::::::::::::::e   n::::n    n::::n
 *   K:::::K K:::::K   e::::::eeeeeeeeeee  e::::::eeeeeeeeeee    n::::n    n::::n
 * KK::::::K  K:::::KKKe:::::::e           e:::::::e             n::::n    n::::n
 * K:::::::K   K::::::Ke::::::::e          e::::::::e            n::::n    n::::n
 * K:::::::K    K:::::K e::::::::eeeeeeee   e::::::::eeeeeeee    n::::n    n::::n
 * K:::::::K    K:::::K  ee:::::::::::::e    ee:::::::::::::e    n::::n    n::::n
 * KKKKKKKKK    KKKKKKK    eeeeeeeeeeeeee      eeeeeeeeeeeeee    nnnnnn    nnnnnn
 * 
 */
package org.nrnb.avalon.cythesaurus.internal.dev;

/**
 * Debug statements output utils<br>
 * 2013-9-19:下午9:54:11
 * 
 * @author Keen<br>
 */
public final class RazLog {
	/**
	 * print the debug informations to standard out <br>
	 * change the configuration see
	 * {@link org.nrnb.avalon.cythesaurus.internal.dev.DebugKey.java}<br>
	 * 2013-9-19:下午9:57:35<br>
	 * <br>
	 * 
	 * @param o print statements
	 */
	public static final void print(Object o) {
		if (DebugKey.isDebug) {
			if (o==null) {
				System.out.println("RazLog -- null");
				return ;
			}
			System.out.println("RazLog -- " + o.toString());
		}
	}

	/**
	 * print the debug informations to standard out <br>
	 * change the configuration see
	 * {@link org.nrnb.avalon.cythesaurus.internal.dev.DebugKey.java}<br>
	 * 2013-9-19:下午9:58:25<br>
	 * <br>
	 * 
	 * @param o print statements
	 * @param mark debug mark 
	 */
	public static final void print(Object o, String mark) {
		if (DebugKey.isDebug) {
			if (o==null) {
				System.out.println("RazLog -- " + mark + " -- null");
				return ;
			}
			System.out.println("RazLog -- " + mark + " -- " + o.toString());
		}
	}
}
