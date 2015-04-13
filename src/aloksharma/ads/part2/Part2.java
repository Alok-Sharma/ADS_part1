package aloksharma.ads.part2;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class Part2 {

	public static void main(String[] args) {
		
		BinaryTrie trie = new BinaryTrie();
		trie.insert("000", "alok");
		trie.insert("010", "alok");
		trie.insert("011", "alok");
		trie.insert("10", "nikita is lame");
		trie.insert("11", "nikita is so lame haha");
		
		trie.printTrie();
		
		trie.merge(trie.rootNode);
		System.out.println("after merge=====");
		trie.printTrie();
		
//		trie.insert(convertIPToBinary("192.168.1.1"), "182.168.1.1");
//		trie.insert(convertIPToBinary("192.122.1.1"), "182.168.1.1");
//		trie.insert(convertIPToBinary("191.2.2.2"), "192.1.1.2");
//		trie.insert(convertIPToBinary("191.3.2.2"), "193.1.1.2");
//		trie.printTrie();
//		TrieNode result = trie.search(convertIPToBinary("191.3.2.1"));
//		String longestPrefix = result.getLongestPrefix(convertIPToBinary("191.3.2.1"));
//		System.out.println(result.nextHopIP);
//		System.out.println(longestPrefix);
//		System.out.println(trie.convertIPToBinary("192.122.1.1"));
//		System.out.println(trie.convertIPToBinary("191.2.2.2"));
//		System.out.println(convertIPToBinary("191.3.2.2"));
	}
	
	/*
	 * Converts String ip to binary, duh.
	 */
	public static String convertIPToBinary(String inputIp){
		byte[] bytes;
		try {
			bytes = InetAddress.getByName(inputIp).getAddress();
			String data_out = new BigInteger(1, bytes).toString(2);
			return data_out;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
