package spelling;

import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author Charuroopa
 *
 */
public class AutoCompleteDictionaryTrie implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size;
    private int numWords;


    public AutoCompleteDictionaryTrie()
	{
		root = new TrieNode();
		numWords = 0;
	}


	/** Insert a word into the trie.

	public boolean addWord(String word)
	{
	    TrieNode temp = root;
	    word = word.toLowerCase();
	    int iter =0;
	    char []wordArray = word.toCharArray();

	    while(iter!=word.length()) {
	    	if(temp.getChild(wordArray[iter])!=null)
	    		temp= temp.getChild(wordArray[iter]);
	    	else
	    		break;
	    	iter++;
	    }

	    if(iter == word.length() && temp.endsWord())
	    	return false; // already set the word

	    while(iter!=word.length()) {
	    	temp.insert(wordArray[iter]);
	    	temp = temp.getChild(wordArray[iter]);
	    	iter++;
	    }

	    temp.setEndsWord(true);
	    numWords++;
	    return true;
	}

	/**
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size()
	{
	    //TODO: Implement this method
		return numWords;
	}


	/** Returns whether the string is a word in the trie */
	@Override
	public boolean isWord(String s)
	{
	    // TODO: Implement this method
		s = s.toLowerCase();

		TrieNode temp = root;
		int iter = 0;
		char []wordArray = s.toCharArray();
		while(iter!=s.length() && temp.getChild(wordArray[iter])!=null) {
			temp = temp.getChild(wordArray[iter]);
			iter++;
		}
		if(!(iter == s.length()) && temp.getChild(wordArray[iter])==null)
			return false;
		if(iter == s.length() && temp.endsWord())
			return true;

		return false;
	}

	/**
	 *  * Returns up to the n "best" predictions, including the word itself,
     * in terms of length
     * If this string is not in the trie, it returns null.
     * @param text The text to use at the word stem
     * @param n The maximum number of predictions desired.
     * @return A list containing the up to n best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions)
     {

    	 prefix = prefix.toLowerCase();
    	 List<String> suggestions = new ArrayList<String>();


    	TrieNode temp = root;
 		int iter = 0;
 		char []wordArray = prefix.toCharArray();
 		if(!prefix.isEmpty()) {
	 		while(temp.getChild(wordArray[iter])!=null) {
	 			temp = temp.getChild(wordArray[iter]);
	 			if(temp.getText().equals(prefix))
	 					break;
	 			iter++;
	 		}
	 		if(temp == root)
	 			return suggestions;

 		}

 		Queue<TrieNode> bfs = new LinkedList<TrieNode>();
 		bfs.add(temp);
 		while(!bfs.isEmpty()) {
 			TrieNode currNode = bfs.peek();
 			if(currNode.endsWord())
				suggestions.add(currNode.getText());
 			bfs.remove();
 			for(char c: currNode.getValidNextCharacters()) {
 				TrieNode currChild = currNode.getChild(c);
 				if(numCompletions == suggestions.size())
 					break;
 				bfs.add(currChild);
 			}
 			if(numCompletions == suggestions.size())
					break;
 		}

 		System.out.println(suggestions);
        return suggestions;
     }

 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 	}

 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null)
 			return;

 		System.out.println(curr.getText());

 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}


}
