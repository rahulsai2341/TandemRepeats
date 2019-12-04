//package com.survey.methods;
//
//import java.util.*;
//
////import com.survey.methods.SuffixArray.Suffix;
//
///******************************************************************************
// *  Compilation:  javac Manber.java
// *  Execution:    java Manber < text.txt
// *  Dependencies: StdIn.java
// *  
// *  Reads a text corpus from stdin and sorts the suffixes
// *  in subquadratic time using a variant of Manber's algorithm.
// *
// ******************************************************************************/
//
//public class Manber {
//    private int n;               // length of input string
//    private String text;         // input text
//    private int[] index;         // offset of ith string in order
//    private int[] rank;          // rank of ith string
//    private int[] newrank;       // rank of ith string (temporary)
//    private int offset;
//   
//    private int length_suffix() {
//        return text.length();
//    }
//    private char charAt(int i) {
//        return text.charAt(i);
//    }
//    
//    public Manber(String s) {
//        n = s.length();
//        text = s;
//        index   = new int[n+1];
//        rank    = new int[n+1];
//        newrank = new int[n+1];
//
//        // sentinels
//        index[n] = n;
//        rank[n] = -1;
//
//        msd();
//        doit();
//    }
//
//    /**
//     * Returns the length of the input string.
//     * @return the length of the input string
//     */
//    public int length() {
//        return n;
//    }
//
//
//    /**
//     * Returns the index into the original string of the <em>i</em>th smallest suffix.
//     * That is, {@code text.substring(sa.index(i), n)}
//     * is the <em>i</em>th smallest suffix.
//     * @param i an integer between 0 and <em>n</em>-1
//     * @return the index into the original string of the <em>i</em>th smallest suffix
//     * @throws java.lang.IllegalArgumentException unless 0 <= <em>i</em> < <em>n</em>
//     */
//    public int index(int i) {
//        if (i < 0 || i >= n) throw new IllegalArgumentException();
//        return index[i];
//    }
//
//    /**
//     * Returns the <em>i</em>th smallest suffix as a string.
//     * @param i the index
//     * @return the <em>i</em> smallest suffix as a string
//     * @throws java.lang.IllegalArgumentException unless 0 <= <em>i</em> < <em>n</em>
//     */
//    public String select(int i) {
//        if (i < 0 || i >= n) throw new IllegalArgumentException();
//        return text.substring(index[i]);
//    }
//
//    // do one pass of msd sorting by rank at given offset
//    private void doit() {
//        for (offset = 1; offset < n; offset += offset) {
//            int count = 0;
//            for (int i = 1; i <= n; i++) {
//                if (rank[index[i]] == rank[index[i-1]]) count++;
//                else if (count > 0) {
//                    // sort
//                    int left = i-1-count;
//                    int right = i-1;
//                    quicksort(left, right);
//
//                    // now fix up ranks
//                    int r = rank[index[left]];
//                    for (int j = left + 1; j <= right; j++) {
//                        if (less(index[j-1], index[j]))  {
//                            r = rank[index[left]] + j - left; 
//                        }
//                        newrank[index[j]] = r;
//                    }
//
//                    // copy back - note can't update rank too eagerly
//                    for (int j = left + 1; j <= right; j++) {
//                        rank[index[j]] = newrank[index[j]];
//                    }
//
//                    count = 0;
//                }
//            }
//        }
//    }
//
//    // sort by leading char, assumes extended ASCII (256 values)
//    private void msd() {
//        final int R = 256;
//
//        // calculate frequencies
//        int[] freq = new int[R];
//        for (int i = 0; i < n; i++)
//            freq[text.charAt(i)]++;
//
//        // calculate cumulative frequencies
//        int[] cumm = new int[R];
//        for (int i = 1; i < R; i++)
//            cumm[i] = cumm[i-1] + freq[i-1];
//
//        // compute ranks
//        for (int i = 0; i < n; i++)
//            rank[i] = cumm[text.charAt(i)];
//
//        // sort by first char
//        for (int i = 0; i < n; i++)
//            index[cumm[text.charAt(i)]++] = i;
//    }
//
//
//
///******************************************************************************
// *  Helper functions for comparing suffixes.
// ******************************************************************************/
//
//  /**********************************************************************
//   * Is the substring text[v..n] lexicographically less than the
//   * substring text[w..n] ?
//   **********************************************************************/
//    private boolean less(int v, int w) {
//        return rank[v + offset] < rank[w + offset];
//    }
//
//
///******************************************************************************
// *  Quicksort code from Sedgewick 7.1, 7.2.
// ******************************************************************************/
//
//    // swap pointer sort indices
//    private void exch(int i, int j) {
//        int swap = index[i];
//        index[i] = index[j];
//        index[j] = swap;
//    }
//
//
//    // SUGGEST REPLACING WITH 3-WAY QUICKSORT SINCE ELEMENTS ARE
//    // RANKS AND THERE MAY BE DUPLICATES
//    private void quicksort(int lo, int hi) { 
//        if (hi <= lo) return;
//        int i = partition(lo, hi);
//        quicksort(lo, i-1);
//        quicksort(i+1, hi);
//    }
//
//    private int partition(int lo, int hi) {
//        int i = lo-1, j = hi;
//        int v = index[hi];
//
//        while (true) { 
//
//            // find item on left to swap
//            while (less(index[++i], v))
//                if (i == hi) break;   // redundant
//
//            // find item on right to swap
//            while (less(v, index[--j]))
//                if (j == lo) break;
//
//            // check if pointers cross
//            if (i >= j) break;
//
//            exch(i, j);
//        }
//
//        // swap with partition element
//        exch(i, hi);
//
//        return i;
//    }
//
//
//    /**
//     * Unit tests the {@code Manber} data type.
//     *
//     * @param args the command-line arguments
//     */
//    
//    public int lcp(int i, ArrayList<String> suffixes) {
//        if (i < 1 || i >= suffixes.size()) throw new IllegalArgumentException();
//        return lcpSuffix(suffixes.get(i), suffixes.get(i-1));
//    }
//
//    // longest common prefix of s and t
//    private static int lcpSuffix(String s, String t) {
//        int n = Math.min(s.length(), t.length());
//        for (int i = 0; i < n; i++) {
//            if (s.charAt(i) != t.charAt(i)) return i;
//        }
//        return n;
//    }
// 
//    
//    public static void main(String[] args) {
//    	Parser p = new Parser();
//    	String s = p.fileToHashMap("database6.txt");
//    	long startTime = System.currentTimeMillis();
//    	
//		long startTime_build_manber = System.currentTimeMillis();
//        Manber suffix = new Manber(s);
//        
//        ArrayList<String> suffix_array = new ArrayList<String>();
//        Suffix[] suff = new Suffix[s.length()];
//        for (int i = 0; i < s.length(); i++) {
//            int index = suffix.index(i);
//            String ith = s.substring(index, Math.min(index + 50, s.length()));
//            suff[i] = new Suffix(s,index);
//            //System.out.println(i +"," +index + "," + ith);
//        }
//        
//        SuffixArray sa = new SuffixArray(suff);
//		long endTime_build_manber = System.currentTimeMillis();
//	    System.out.println("Suffix array with manber build time: " + (endTime_build_manber - startTime_build_manber));
//
//		long startTime_get_repeat = System.currentTimeMillis();
//        String lrs = "";
//        for (int i = 1; i < s.length(); i++) {
//            int length = sa.lcp(i);
//            if (length > lrs.length()) {
//                lrs = s.substring(sa.index(i), sa.index(i) + length);
//            }
//        }
//        System.out.println(lrs);
//	    long endTime = System.currentTimeMillis();
//	    System.out.println("Suffix array with manber finding repeat time: " + (endTime - startTime_get_repeat));
//	    System.out.println("Suffix array with manber execution time: " + (endTime - startTime));
//
//    }
//
//}
//
//class SuffixArray {
//    private Suffix[] suffixes;
//
//    
//    public SuffixArray(String text) {
//        int n = text.length();
//        this.suffixes = new Suffix[n];
//        for (int i = 0; i < n; i++)
//            suffixes[i] = new Suffix(text, i);
//        Arrays.sort(suffixes);
//    }
//    
//    public SuffixArray(Suffix[] suff) {
//    	this.suffixes = suff;
//    }
//
//    /**
//     * Returns the length of the input string.
//     * @return the length of the input string
//     */
//    public int length() {
//        return suffixes.length;
//    }
//
//
//    /**
//     * Returns the index into the original string of the <em>i</em>th smallest suffix.
//     * That is, {@code text.substring(sa.index(i))} is the <em>i</em>th smallest suffix.
//     * @param i an integer between 0 and <em>n</em>-1
//     * @return the index into the original string of the <em>i</em>th smallest suffix
//     * @throws java.lang.IllegalArgumentException unless {@code 0 <= i < n}
//     */
//    public int index(int i) {
//        if (i < 0 || i >= suffixes.length) throw new IllegalArgumentException();
//        return suffixes[i].index;
//    }
//
//
//    /**
//     * Returns the length of the longest common prefix of the <em>i</em>th
//     * smallest suffix and the <em>i</em>-1st smallest suffix.
//     * @param i an integer between 1 and <em>n</em>-1
//     * @return the length of the longest common prefix of the <em>i</em>th
//     * smallest suffix and the <em>i</em>-1st smallest suffix.
//     * @throws java.lang.IllegalArgumentException unless {@code 1 <= i < n}
//     */
//    public int lcp(int i) {
//        if (i < 1 || i >= suffixes.length) throw new IllegalArgumentException();
//        return lcpSuffix(suffixes[i], suffixes[i-1]);
//    }
//
//    // longest common prefix of s and t
//    private static int lcpSuffix(Suffix s, Suffix t) {
//        int n = Math.min(s.length(), t.length());
//        for (int i = 0; i < n; i++) {
//            if (s.charAt(i) != t.charAt(i)) return i;
//        }
//        return n;
//    }
//
//    /**
//     * Returns the <em>i</em>th smallest suffix as a string.
//     * @param i the index
//     * @return the <em>i</em> smallest suffix as a string
//     * @throws java.lang.IllegalArgumentException unless {@code 0 <= i < n}
//     */
//    public String select(int i) {
//        if (i < 0 || i >= suffixes.length) throw new IllegalArgumentException();
//        return suffixes[i].toString();
//    }
//
//    /**
//     * Returns the number of suffixes strictly less than the {@code query} string.
//     * We note that {@code rank(select(i))} equals {@code i} for each {@code i}
//     * between 0 and <em>n</em>-1.
//     * @param query the query string
//     * @return the number of suffixes strictly less than {@code query}
//     */
//    public int rank(String query) {
//        int lo = 0, hi = suffixes.length - 1;
//        while (lo <= hi) {
//            int mid = lo + (hi - lo) / 2;
//            int cmp = compare(query, suffixes[mid]);
//            if (cmp < 0) hi = mid - 1;
//            else if (cmp > 0) lo = mid + 1;
//            else return mid;
//        }
//        return lo;
//    }
//
//    // compare query string to suffix
//    private static int compare(String query, Suffix suffix) {
//        int n = Math.min(query.length(), suffix.length());
//        for (int i = 0; i < n; i++) {
//            if (query.charAt(i) < suffix.charAt(i)) return -1;
//            if (query.charAt(i) > suffix.charAt(i)) return +1;
//        }
//        return query.length() - suffix.length();
//    }
//}
//
//class Suffix implements Comparable<Suffix> {
//    public final String text;
//    public final int index;
//
//    public Suffix(String text, int index) {
//        this.text = text;
//        this.index = index;
//    }
//    public int length() {
//        return text.length() - index;
//    }
//    public char charAt(int i) {
//        return text.charAt(index + i);
//    }
//
//    public int compareTo(Suffix that) {
//        if (this == that) return 0;  // optimization
//        int n = Math.min(this.length(), that.length());
//        for (int i = 0; i < n; i++) {
//            if (this.charAt(i) < that.charAt(i)) return -1;
//            if (this.charAt(i) > that.charAt(i)) return +1;
//        }
//        return this.length() - that.length();
//    }
//
//    public String toString() {
//        return text.substring(index);
//    }
//}
