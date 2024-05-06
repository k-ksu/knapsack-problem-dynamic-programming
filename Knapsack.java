//Ksenia Korchagina
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Knapsack {
    public static class Item {
        int weight;
        int value;

        public Item(int weight, int value) {
            this.weight = weight;
            this.value = value;
        }
    }

    public static void reverseList(List<Integer> list) {
        int left = 0;
        int right = list.size() - 1;

        while (left < right) {
            int temp = list.get(left);
            list.set(left, list.get(right));
            list.set(right, temp);
            left++;
            right--;
        }
    }


    public static int[][] knapsack(int capacity, List<Integer> weights, List<Integer> values) {
        int n = weights.size();
        int[][] dp = new int[n + 1][capacity + 1];
        boolean[][] keep = new boolean[n + 1][capacity + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= capacity; j++) {
                if (weights.get(i - 1) <= j) {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - weights.get(i - 1)] + values.get(i - 1));
                    if (dp[i][j] == dp[i - 1][j - weights.get(i - 1)] + values.get(i - 1)) {
                        keep[i][j] = true;
                    }
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        // Backtrack to find selected items
        List<Integer> selectedItems = new ArrayList<>();
        int i = n, j = capacity;
        while (i > 0 && j > 0) {
            if (keep[i][j]) {
                selectedItems.add(i - 1 + 1); // Adjust index to 0-based
                j -= weights.get(i - 1);
            }
            i--;
        }

        reverseList(selectedItems);

        // Return max value and list of selected item indexes
        return new int[][]{new int[]{dp[n][capacity]}, selectedItems.stream().mapToInt(Integer::intValue).toArray()};
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input
        int amountOfItems = scanner.nextInt();
        int capacity = scanner.nextInt();
        scanner.nextLine();

        List<Integer> weights = new ArrayList<>();
        for (int i = 0; i < amountOfItems; i++){
            weights.add(scanner.nextInt());
        }
        scanner.nextLine();

        List<Integer> values = new ArrayList<>();
        for (int i = 0; i < amountOfItems; i++){
            values.add(scanner.nextInt());
        }


        int[][] result = knapsack(capacity, weights, values);
        int maxValue = result[0][0];
        int[] selectedItems = result[1];

        System.out.println(selectedItems.length);
        for (int i : selectedItems) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
}
