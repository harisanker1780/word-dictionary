package com.wd.service.impl.trie;

import java.util.LinkedList;

public class TrieNode {
	private char c;
	private boolean isLeafNode;
	private LinkedList<TrieNode> childrens;
	
	public TrieNode() {
		
	}
	
	public TrieNode(char c) {
		this.c = c;
		this.isLeafNode = false;
	}
	
	protected TrieNode getChildNode(char c) {
		if(childrens != null) {
			for(TrieNode child : childrens) {
				if(child.c == c)
					return child;
			}
		}
		return null;
	}
	
	protected void addChild(TrieNode node) {
		if(childrens == null) {
			this.childrens = new LinkedList<TrieNode>(); 
		}
		
		this.childrens.add(node);
	}

	public char getC() {
		return c;
	}

	public void setC(char c) {
		this.c = c;
	}

	public boolean isLeafNode() {
		return isLeafNode;
	}

	public void setLeafNode(boolean isLeafNode) {
		this.isLeafNode = isLeafNode;
	}

	public LinkedList<TrieNode> getChildrens() { 
		return childrens; 
	}
	 
	public void setChildrens(LinkedList<TrieNode> childrens) {
		this.childrens = childrens;
	}
	
}
