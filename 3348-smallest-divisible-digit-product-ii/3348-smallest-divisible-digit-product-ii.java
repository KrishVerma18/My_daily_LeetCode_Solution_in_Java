class Solution {
    public String smallestNumber(String num, long t) {
        Pair<Map<Integer, Integer>, Boolean> p = getPrimeCount(t);
        Map<Integer, Integer> need = p.getKey();

        if (!p.getValue())
            return "-1";

        Map<Integer, Integer> cnt = getFactorCount(need);

        if (sumValues(cnt) > num.length())
            return construct(cnt);

        Map<Integer, Integer> prefix = getPrimeCount(num);
        int zero = num.indexOf('0');

        if (zero == -1) {
            zero = num.length();

            if (isSubset(need, prefix))
                return num;
        }

        for (int i = num.length() - 1; i >= 0; i--) {
            int d = num.charAt(i) - '0';

            prefix = subtract(prefix, FACTOR_COUNTS.get(d));

            int space = num.length() - 1 - i;

            if (i > zero)
                continue;

            for (int bigger = d + 1; bigger < 10; bigger++) {
                Map<Integer, Integer> left = getFactorCount(
                    subtract(
                        subtract(need, prefix),
                        FACTOR_COUNTS.get(bigger)
                    )
                );

                if (sumValues(left) <= space) {
                    int ones = space - sumValues(left);

                    return num.substring(0, i)
                        + bigger
                        + "1".repeat(ones)
                        + construct(left);
                }
            }
        }

        Map<Integer, Integer> left = getFactorCount(need);

        return "1".repeat(num.length() + 1 - sumValues(left))
            + construct(left);
    }

    private static final Map<Integer, Map<Integer, Integer>> FACTOR_COUNTS = Map.of(
        0, Map.of(),
        1, Map.of(),
        2, Map.of(2, 1),
        3, Map.of(3, 1),
        4, Map.of(2, 2),
        5, Map.of(5, 1),
        6, Map.of(2, 1, 3, 1),
        7, Map.of(7, 1),
        8, Map.of(2, 3),
        9, Map.of(3, 2)
    );

    private Pair<Map<Integer, Integer>, Boolean> getPrimeCount(long t) {
        Map<Integer, Integer> count = new HashMap<>(
            Map.of(2, 0, 3, 0, 5, 0, 7, 0)
        );

        for (int p : new int[]{2, 3, 5, 7}) {
            while (t % p == 0) {
                t /= p;
                count.put(p, count.get(p) + 1);
            }
        }

        return new Pair<>(count, t == 1);
    }

    private Map<Integer, Integer> getPrimeCount(String num) {
        Map<Integer, Integer> count = new HashMap<>(
            Map.of(2, 0, 3, 0, 5, 0, 7, 0)
        );

        for (char c : num.toCharArray()) {
            Map<Integer, Integer> f = FACTOR_COUNTS.get(c - '0');

            for (Map.Entry<Integer, Integer> e : f.entrySet()) {
                count.merge(e.getKey(), e.getValue(), Integer::sum);
            }
        }

        return count;
    }

    private Map<Integer, Integer> getFactorCount(
        Map<Integer, Integer> count
    ) {
        int count8 = count.get(2) / 3;
        int rem2 = count.get(2) % 3;

        int count9 = count.get(3) / 2;
        int rem3 = count.get(3) % 2;

        int count4 = rem2 / 2;
        int count2 = rem2 % 2;

        int count6 = 0;

        if (count2 == 1 && rem3 == 1) {
            count2 = 0;
            rem3 = 0;
            count6 = 1;
        }

        if (rem3 == 1 && count4 == 1) {
            count2 = 1;
            count6 = 1;
            rem3 = 0;
            count4 = 0;
        }

        return Map.of(
            2, count2,
            3, rem3,
            4, count4,
            5, count.get(5),
            6, count6,
            7, count.get(7),
            8, count8,
            9, count9
        );
    }

    private String construct(Map<Integer, Integer> factors) {
        StringBuilder sb = new StringBuilder();

        for (int i = 2; i < 10; i++)
            sb.append(String.valueOf(i).repeat(factors.get(i)));

        return sb.toString();
    }

    private boolean isSubset(
        Map<Integer, Integer> a,
        Map<Integer, Integer> b
    ) {
        for (Map.Entry<Integer, Integer> e : a.entrySet()) {
            if (b.get(e.getKey()) < e.getValue())
                return false;
        }

        return true;
    }

    private Map<Integer, Integer> subtract(
        Map<Integer, Integer> a,
        Map<Integer, Integer> b
    ) {
        Map<Integer, Integer> res = new HashMap<>(a);

        for (Map.Entry<Integer, Integer> e : b.entrySet()) {
            int key = e.getKey();
            int value = e.getValue();

            res.put(
                key,
                Math.max(0, res.get(key) - value)
            );
        }

        return res;
    }

    private int sumValues(Map<Integer, Integer> count) {
        int sum = 0;

        for (int x : count.values())
            sum += x;

        return sum;
    }
}