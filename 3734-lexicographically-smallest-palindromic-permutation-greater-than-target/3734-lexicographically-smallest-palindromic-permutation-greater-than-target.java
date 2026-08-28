class Solution {
    public String lexPalindromicPermutation(String s, String target) {
        int n = s.length();
        int[] count = new int[26];
        for (int i = 0; i < n; i++) {
            count[s.charAt(i) - 'a']++;
        }

        int oddCount = 0;
        char midChar = 0;
        for (int i = 0; i < 26; i++) {
            if (count[i] % 2 != 0) {
                oddCount++;
                midChar = (char) ('a' + i);
            }
        }

        if (oddCount > 1) {
            return "";
        }

        int halfLen = n / 2;
        int[] halfCount = new int[26];
        for (int i = 0; i < 26; i++) {
            halfCount[i] = count[i] / 2;
        }

        String bestResult = null;

        for (int i = halfLen; i >= 0; i--) {
            String prefix = target.substring(0, i);
            int[] remHalf = halfCount.clone();
            boolean validPrefix = true;

            for (int j = 0; j < i; j++) {
                int c = prefix.charAt(j) - 'a';
                if (remHalf[c] > 0) {
                    remHalf[c]--;
                } else {
                    validPrefix = false;
                    break;
                }
            }

            if (!validPrefix) continue;

            if (i < halfLen) {
                int startChar = target.charAt(i) - 'a' + 1;
                for (int c = startChar; c < 26; c++) {
                    if (remHalf[c] > 0) {
                        remHalf[c]--;
                        String res = constructSmallest(prefix + (char) ('a' + c), remHalf, midChar, n);
                        if (res.compareTo(target) > 0) {
                            if (bestResult == null || res.compareTo(bestResult) < 0) {
                                bestResult = res;
                            }
                        }
                        remHalf[c]++;
                    }
                }
            } else {
                String res = constructSmallest(prefix, remHalf, midChar, n);
                if (res.compareTo(target) > 0) {
                    if (bestResult == null || res.compareTo(bestResult) < 0) {
                        bestResult = res;
                    }
                }
            }

            if (bestResult != null) {
                return bestResult;
            }
        }

        return "";
    }

    private String constructSmallest(String pref, int[] avail, char mid, int n) {
        StringBuilder sb = new StringBuilder(pref);
        for (int c = 0; c < 26; c++) {
            for (int k = 0; k < avail[c]; k++) {
                sb.append((char) ('a' + c));
            }
        }
        String firstHalf = sb.toString();
        StringBuilder full = new StringBuilder(firstHalf);
        if (n % 2 != 0) {
            full.append(mid);
        }
        for (int i = firstHalf.length() - 1; i >= 0; i--) {
            full.append(firstHalf.charAt(i));
        }
        return full.toString();
    }
}