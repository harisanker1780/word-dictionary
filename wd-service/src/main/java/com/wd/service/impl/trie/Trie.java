package com.wd.service.impl.trie;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Trie {
	
	private TrieNode root;
	
	public Trie() {
		root = new TrieNode(' ');
	}
	
	public Trie(TrieNode root) {
		this.root = root;
	}
	
	public TrieNode getRoot() {
		return root;
	}
	
	public void addKey(String key) {
		if(searchKey(key)) {
			return;
		}
		
		TrieNode current = root;
		for(char c : key.toCharArray()) {
			TrieNode child = current.getChildNode(c);
			if(child != null) {
				current = child;
			} else {
				current.addChild(new TrieNode(c));
				current = current.getChildNode(c);
			}
		}
		current.setLeafNode(true);
	}
	
	public boolean searchKey(String key) {
		TrieNode current = root;
		for(char c : key.toCharArray()) {
			TrieNode child = current.getChildNode(c);
			if(child == null) {
				return false;
			}
			current = child;
		}
		
		return current.isLeafNode();
	}
	
	public List<String> matchingWords(String key, int wordLimit) {
		List<String> words = new ArrayList<String>();
		
		TrieNode current = root;
		char[] array = key.toLowerCase().toCharArray();
		for(char c : array) {
			TrieNode child = current.getChildNode(c);
			if(child == null) {
				return words;
			}
			current = child;
		}
		
		buildMatchingWords(words, current, wordLimit, new String(array).substring(0, array.length - 1));
		return words;
	}
	
	private void buildMatchingWords(List<String> words, TrieNode root, int wordLimit, String prefix) {
		if(words.size() < wordLimit) {
			prefix = prefix + root.getC();
			if(root.isLeafNode()) {
				words.add(prefix);
			}
			
			LinkedList<TrieNode> childrens = root.getChildrens();
			if(childrens != null) {
				for(TrieNode child : childrens) {
					buildMatchingWords(words, child, wordLimit, prefix);
				}
			}
		}
	}

}
