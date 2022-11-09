/**
 * REVERSI GAME ENGINE
 * November 10, 2022
 * Coded by Ethan Chen in a 2-hour time block for fun!
  */

public class Reversi {

    // instance vars
    char[][] grid;
    int n;

    // constructor - initialize nxn gameboard with central pattern
    // X O
    // O X
    public Reversi(int n) throws Exception {

        if(n > 26 || n <= 0) {
            throw new Exception("Invalid game board size: please limit n to the range [1, 25].");
        }

        this.grid = new char[n][n]; // 'X' or 'O' for player pieces, '.' for empty, and '#' for obstacle
        this.n = n;

        // fill grid with empty space
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                this.grid[i][j] = '.';
            }
        }

        // central pattern
        this.grid[n/2][n/2] = 'X';
        this.grid[n/2-1][n/2-1] = 'X';
        this.grid[n/2][n/2-1] = 'O';
        this.grid[n/2-1][n/2] = 'O';
    }

    /**
     * introduce 'blocks' or obstacles where pieces cannot be placed
     */
    public boolean addBlock(int r, int c) {
        if(outOfBounds(r, c) || grid[r][c] != '.') {
            return false;
        } else {
            grid[r][c] = '#';
            return true;
        }
    }

    /**
     * prints out the current game board, formatted
     */
    public void printBoard() {
        // formatting
        int numSpaces = n >= 10 ? 2 : 1;
        for(int i = 0; i < numSpaces; i++) {
            System.out.print(" ");
        }
        System.out.print(" ");
        for(int i = 0; i < n; i++) {
            System.out.print((char) (i + 'A') + " ");
        }

        System.out.println();

        for(int i = 0; i < n; i++) {
            if(numSpaces == 2 && i < 9) System.out.print(" ");
            System.out.print((i + 1) + " ");
            for(int j = 0; j < n; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * checks if there is a possible move for player p at grid location (r, c)
     */
    public boolean checkPossibleMove(char p, int r, int c) {
        if(outOfBounds(r, c)) return false;
        if(grid[r][c] != '.') return false;

        // left
        if(c > 1 && grid[r][c-1] != '.' && grid[r][c-1] != '#' && grid[r][c-1] != p) {
            int currC = c - 2;
            while(currC >= 0) { // check
                if(grid[r][currC] == '.' || grid[r][currC] == '#') break; // if empty or obstacle, invalid
                if(grid[r][currC] == p) return true; // if we find a piece of the same player, there is a valid move
                currC--;
            }
        }

        // right
        if(c < n-2 && grid[r][c+1] != '.' && grid[r][c+1] != '#' && grid[r][c+1] != p) {
            int currC = c + 2;
            while(currC < n) {
                if(grid[r][currC] == '.' || grid[r][currC] == '#') break;
                if(grid[r][currC] == p) return true;
                currC++;
            }
        }

        // up
        if(r > 1 && grid[r-1][c] != '.' && grid[r-1][c] != '#' && grid[r-1][c] != p) {
            int currR = r - 2;
            while(currR >= 0) {
                if(grid[currR][c] == '.' || grid[currR][c] == '#') break;
                if(grid[currR][c] == p) return true;
                currR--;
            }
        }

        // down
        if(r < n-2 && grid[r+1][c] != '.' && grid[r+1][c] != '#' && grid[r+1][c] != p) {
            int currR = r + 2;
            while(currR < n) {
                if(grid[currR][c] == '.' || grid[currR][c] == '#') break;
                if(grid[currR][c] == p) return true;
                currR++;
            }
        }

        // left up
        if(r > 1 && c > 1 && grid[r-1][c-1] != '.' && grid[r-1][c-1] != '#' && grid[r-1][c-1] != p) {
            int currR = r - 2;
            int currC = c - 2;
            while(currR >= 0 && currC >= 0) {
                if(grid[currR][currC] == '.' || grid[currR][currC] == '#') break;
                if(grid[currR][currC] == p) return true;
                currR--;
                currC--;
            }
        }

        // left down
        if(r < n-2 && c > 1 && grid[r+1][c-1] != '.' && grid[r+1][c-1] != '#' && grid[r+1][c-1] != p) {
            int currR = r + 2;
            int currC = c - 2;
            while(currR < n && currC >= 0) {
                if(grid[currR][currC] == '.' || grid[currR][currC] == '#') break;
                if(grid[currR][currC] == p) return true;
                currR++;
                currC--;
            }
        }

        // right up
        if(r > 1 && c < n-2 && grid[r-1][c+1] != '.' && grid[r-1][c+1] != '#' && grid[r-1][c+1] != p) {
            int currR = r - 2;
            int currC = c + 2;
            while(currR >= 0 && currC < n) {
                if(grid[currR][currC] == '.' || grid[currR][currC] == '#') break;
                if(grid[currR][currC] == p) return true;
                currR--;
                currC++;
            }
        }

        // right down
        if(r < n-2 && c < n-2 && grid[r+1][c+1] != '.' && grid[r+1][c+1] != '#' && grid[r+1][c+1] != p) {
            int currR = r + 2;
            int currC = c + 2;
            while(currR < n && currC < n) {
                if(grid[currR][currC] == '.' || grid[currR][currC] == '#') break;
                if(grid[currR][currC] == p) return true;
                currR++;
                currC++;
            }
        }

        return false;
    }

    /**
     * performs a move for player p at grid location (r, c)
     */
    public void makeMove(char p, int r, int c) {
        if(outOfBounds(r, c)) return;
        if(grid[r][c] != '.') return;

        // left
        if(c > 1 && grid[r][c-1] != '.' && grid[r][c-1] != '#' && grid[r][c-1] != p) {
            int currC = c - 2;
            boolean moveFound = false;
            while(currC >= 0) {
                if(grid[r][currC] == '.' || grid[r][currC] == '#') break;
                if(grid[r][currC] == p) {
                    moveFound = true;
                    break;
                }
                currC--;
            }

            // move found, fill with player pieces
            if(moveFound) {
                grid[r][c] = p;
                currC++;
                while(currC < c) {
                    grid[r][currC++] = p;
                }
            }
        }


        // right
        if(c < n-2 && grid[r][c+1] != '.' && grid[r][c+1] != '#' && grid[r][c+1] != p) {
            int currC = c + 2;
            boolean moveFound = false;
            while(currC < n) {
                if(grid[r][currC] == '.' || grid[r][currC] == '#') break;
                if(grid[r][currC] == p) {
                    moveFound = true;
                    break;
                }
                currC++;
            }

            // move found, fill with player pieces
            if(moveFound) {
                grid[r][c] = p;
                currC--;
                while(currC > c) {
                    grid[r][currC--] = p;
                }
            }
        }


        // up
        if(r > 1 && grid[r-1][c] != '.' && grid[r-1][c] != '#' && grid[r-1][c] != p) {
            int currR = r - 2;
            boolean moveFound = false;
            while(currR >= 0) {
                if(grid[currR][c] == '.' || grid[currR][c] == '#') break;
                if(grid[currR][c] == p) {
                    moveFound = true;
                    break;
                }
                currR--;
            }

            // move found, fill with player pieces
            if(moveFound) {
                grid[r][c] = p;
                currR++;
                while(currR < r) {
                    grid[currR++][c] = p;
                }
            }
        }


        // down
        if(r < n-2 && grid[r+1][c] != '.' && grid[r+1][c] != '#' && grid[r+1][c] != p) {
            int currR = r + 2;
            boolean moveFound = false;
            while(currR < n) {
                if(grid[currR][c] == '.' || grid[currR][c] == '#') break;
                if(grid[currR][c] == p) {
                    moveFound = true;
                    break;
                }
                currR++;
            }

            // move found, fill with player pieces
            if(moveFound) {
                grid[r][c] = p;
                currR--;
                while(currR > r) {
                    grid[currR--][c] = p;
                }
            }
        }


        // left up
        if(r > 1 && c > 1 && grid[r-1][c-1] != '.' && grid[r-1][c-1] != '#' && grid[r-1][c-1] != p) {
            int currR = r - 2;
            int currC = c - 2;
            boolean moveFound = false;
            while(currR >= 0 && currC >= 0) {
                if(grid[currR][currC] == '.' || grid[currR][currC] == '#') break;
                if(grid[currR][currC] == p) {
                    moveFound = true;
                    break;
                }
                currR--;
                currC--;
            }

            // move found, fill with player pieces
            if(moveFound) {
                grid[r][c] = p;
                currR++;
                currC++;
                while(currR < r) {
                    grid[currR++][currC++] = p;
                }
            }
        }


        // left down
        if(r < n-2 && c > 1 && grid[r+1][c-1] != '.' && grid[r+1][c-1] != '#' && grid[r+1][c-1] != p) {
            int currR = r + 2;
            int currC = c - 2;
            boolean moveFound = false;
            while(currR < n && currC >= 0) {
                if(grid[currR][currC] == '.' || grid[currR][currC] == '#') break;
                if(grid[currR][currC] == p) {
                    moveFound = true;
                    break;
                }
                currR++;
                currC--;
            }

            // move found, fill with player pieces
            if(moveFound) {
                grid[r][c] = p;
                currR--;
                currC++;
                while(currR > r) {
                    grid[currR--][currC++] = p;
                }
            }
        }


        // right up
        if(r > 1 && c < n-2 && grid[r-1][c+1] != '.' && grid[r-1][c+1] != '#' && grid[r-1][c+1] != p) {
            int currR = r - 2;
            int currC = c + 2;
            boolean moveFound = false;
            while(currR >= 0 && currC < n) {
                if(grid[currR][currC] == '.' || grid[currR][currC] == '#') break;
                if(grid[currR][currC] == p) {
                    moveFound = true;
                    break;
                }
                currR--;
                currC++;
            }

            // move found, fill with player pieces
            if(moveFound) {
                grid[r][c] = p;
                currR++;
                currC--;
                while(currR < r) {
                    grid[currR++][currC--] = p;
                }
            }
        }


        // right down
        if(r < n-2 && c < n-2 && grid[r+1][c+1] != '.' && grid[r+1][c+1] != '#' && grid[r+1][c+1] != p) {
            int currR = r + 2;
            int currC = c + 2;
            boolean moveFound = false;
            while(currR < n && currC < n) {
                if(grid[currR][currC] == '.' || grid[currR][currC] == '#') break;
                if(grid[currR][currC] == p) {
                    moveFound = true;
                    break;
                }
                currR++;
                currC++;
            }

            // move found, fill with player pieces
            if(moveFound) {
                grid[r][c] = p;
                currR--;
                currC--;
                while(currR > r) {
                    grid[currR--][currC--] = p;
                }
            }
        }
    }

    /**
     * returns true if a player still has moves they can play
     */
    public boolean hasValidMoves(char p) {
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(checkPossibleMove(p, i, j)) return true; // check moves for all positions, if any work return true
            }
        }

        return false;
    }

    /**
     * returns true if there are still empty cells on teh board
     */
    public boolean hasEmptyCells() {
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(grid[i][j] == '.') return true; // return true if we find an empty cell
            }
        }

        return false;
    }

    /**
     * checks which player currently has more pieces on the board
     */
    public char winner() {
        int xCount = 0;
        int oCount = 0;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(grid[i][j] == 'X') xCount++; // count x
                if(grid[i][j] == 'O') oCount++; // count o
            }
        }

        return xCount > oCount ? 'X' : 'O'; // return player with more pieces
    }

    /**
     * helper method to check if a coordinate is in range for the game board
     */
    private boolean outOfBounds(int r, int c) {
        if(r < 0 || r >= n || c < 0 || c >= n) {
            return true;
        } else {
            return false;
        }
    }

}