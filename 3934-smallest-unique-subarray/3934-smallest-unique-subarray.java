class Solution {
    private int[] nums;
    private long base;
    private long modulo;
    private long[] powers;
    private Map<Long, Integer> hashValues;

    public int smallestUniqueSubarray(int[] nums) {
        this.nums = nums;
        this.base = 19L;
        this.modulo = 1_000_000_007L;
        this.powers = new long[nums.length + 1];
        this.hashValues = new HashMap<>();

        int minPossibleLen = nums.length; // Base case.

        int minLen = 1, maxLen = nums.length; // For binary search usage.

        while (minLen <= maxLen) {
            int midLen = minLen + (maxLen - minLen) / 2;

            if (checkUniqueness(midLen)) {
                if (midLen < minPossibleLen) {
                    minPossibleLen = midLen;
                }
                maxLen = midLen - 1;
            } else {
                minLen = midLen + 1;
            }
        }

        return minPossibleLen;
    }

    private boolean checkUniqueness(int subarrayLen) {
        // Only need to reset leftmost power to 1 before usage.
        powers[0] = 1L; // Because powers are calculated by bottom-up.

        for (int idx = 1; idx <= nums.length; idx++) {
            powers[idx] = (powers[idx - 1] * base) % modulo;
        }

        long currentHash = 0L;
        for (int idx = 0; idx < subarrayLen; idx++) {
            currentHash = (currentHash * base) % modulo;
            currentHash = (currentHash + nums[idx]) % modulo;
            if (currentHash < 0) {
                currentHash += modulo;
            }
        }

        hashValues.clear(); // Clear before usage.
        hashValues.put(currentHash, 1);

        for (int idx = 1; idx <= nums.length - subarrayLen; idx++) {
            // Window shifts: deduct leftmost num's share from hash value.
            long removedShare = (powers[subarrayLen - 1] * (nums[idx - 1] % modulo)) % modulo;
            currentHash = (currentHash - removedShare) % modulo;
            if (currentHash < 0) {
                currentHash += modulo;
            }

            currentHash = (currentHash * base) % modulo;
            currentHash = (currentHash + (nums[idx + subarrayLen - 1] % modulo)) % modulo;
            if (currentHash < 0) {
                currentHash += modulo;
            }

            hashValues.put(currentHash, hashValues.getOrDefault(currentHash, 0) + 1);
        }

        return hashValues.containsValue(1);
    }
}