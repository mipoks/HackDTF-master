package test;

public class Graph_map {
    public static void print_matrix(int[][] matrix) { // print our arr
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + "  ");
            }
            System.out.println();
        }
    }

    public static int[][] map(int[][] matrix) { // matrix without zero value
        int up = -1;
        int down = -1;
        int left = 200;
        int right = -1;

        for (int i = 0; i < matrix.length; i++) { // for UP
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 1) {
                    down = i;
                    if (up == -1) {
                        up = i;
                    }
                    if (left > j) {
                        left = j;
                    }
                    if (right < j) {
                        right = j;
                    }
                }
            }
        }
        int[][] new_matrix = new int[down - up + 1][right - left + 1];
        for (int i = up; i <= down; i++) {
            for (int j = left; j <= right; j++) {
                new_matrix[i - up][j - left] = matrix[i][j];
            }
        }
        return new_matrix;
    }

    public static int[][] add_boss_start(int[][] matrix) { // Добавление босса(-2), начального поля(-1)
        int[][] finish_matrix;
        if (matrix.length > matrix[0].length) {
            finish_matrix = new int[matrix.length + 2][matrix[0].length];
            int a = (int)(Math.random() * 1);
            for (int i = 0; i < matrix[0].length; i++) {
                if (matrix[0][i] == 1){
                    finish_matrix[0][i] = -2 + a;
                    break;
                }
            }
            for (int i = 0; i < matrix[0].length; i++) {
                if (matrix[matrix.length - 1][i] == 1) {
                    finish_matrix[finish_matrix.length - 1][i] = -1 - a;
                    break;
                }
            }
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                    finish_matrix[i+1][j] = matrix[i][j];
                }
            }
        } else {
            finish_matrix = new int[matrix.length][matrix[0].length + 2];
            int a = (int)(Math.random() * 1);
            for (int i = 0; i < matrix.length; i++) {
                if (matrix[i][0] == 1){
                    finish_matrix[i][0] = -2 + a;
                    break;
                }
            }
            for (int i = 0; i < matrix[0].length; i++) {
                if (matrix[i][matrix.length - 1] == 1) {
                    finish_matrix[i][finish_matrix[0].length - 1] = - 1 - a;
                    break;
                }
            }
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                    finish_matrix[i][j+1] = matrix[i][j];
                }
            }
        }
        return finish_matrix;
    }


    public static int[] count_doors(int[][] matrix, int i, int j) { // Двери в которые можно перейти
        int[] count_and_value_graph = new int[5]; // 0 = count for; 1 = [i - 1][j]; 2 = [i + 1][j]; 3 = [i][j - 1]; 4 = [i][j + 1] (каждый эллемент массива, что означает)

        if (matrix[i - 1][j] == 0) {
            count_and_value_graph[0]++;
            count_and_value_graph[1]++;
        }
        if (matrix[i + 1][j] == 0) {
            count_and_value_graph[0]++;
            count_and_value_graph[2]++;
        }
        if (matrix[i][j + 1] == 0) {
            count_and_value_graph[0]++;
            count_and_value_graph[3]++;
        }
        if (matrix[i][j - 1] == 0) {
            count_and_value_graph[0]++;
            count_and_value_graph[4]++;
        }

        return count_and_value_graph;
    }

    //TODO: где нет прохода там блок fixture
    public static void main(String[] args) {
        int map_size = 12; // количество уровней
        int[][] map_matrix = new int[map_size * 2 + 1][map_size * 2 + 1];
        int shift_i = 0; // сдвиг i
        int shift_j = 0; // сдвиг j
        int max_count_open_doors = count_doors(map_matrix, map_size, map_size)[0]; // количество дверей в которые можно пройти
        map_matrix[map_size][map_size] = 1; // начальное значение с которого мы наченали идти
        int i = map_size;
        int j = map_size;
        while (i < map_matrix.length - 1) {
            while (i < map_matrix.length - 1) {
                int count_open_door = 1 + (int) (Math.random() * max_count_open_doors); // случайное количество дверей в ккоторые можно перейти
                shift_i += count_open_door;
                shift_j += count_open_door;
                i += count_open_door;
                j += count_open_door;
                max_count_open_doors = count_doors(map_matrix, i - shift_i, j - shift_j)[0];

                // Заполнение единицами
                if (count_doors(map_matrix, i - shift_i, j - shift_j)[1] == 1) {
                    map_matrix[i - shift_i - 1][j - shift_j] = 1;
                }
                if (count_doors(map_matrix, i - shift_i, j - shift_j)[2] == 1) {
                    map_matrix[i - shift_i + 1][j - shift_j] = 1;
                }
                if (count_doors(map_matrix, i - shift_i, j - shift_j)[3] == 1) {
                    map_matrix[i - shift_i][j - shift_j - 1] = 1;
                }
                if (count_doors(map_matrix, i - shift_i, j - shift_j)[4] == 1) {
                    map_matrix[i - shift_i][j - shift_j + 1] = 1;
                }

                while (true) { // рандомный переход в дверь ( выбирается случайно (по принципу 4 merge в css) начиная с левой стороны и по часовой стрелки
                    int next_doors = 1 + (int) (Math.random() * 4);

                    if (next_doors == 1) {
                        shift_i--;

                        break;
                    }
                    if (next_doors == 2) {
                        shift_j--;
                        break;
                    }
                    if (next_doors == 3) {
                        shift_i++;
                        break;
                    }
                    if (next_doors == 4) {
                        shift_j--;
                        break;
                    }
                }
            }
        }
        print_matrix(map_matrix); // начальная карта
        print_matrix(add_boss_start(map(map_matrix))); //конечный результат

    }
}

