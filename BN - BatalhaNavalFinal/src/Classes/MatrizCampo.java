package Classes;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author André
 */
public class MatrizCampo {

    private int iBombas = 10;
    public int[][] iMatrizCampo = new int[7][7];

    public MatrizCampo() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                iMatrizCampo[i][j] = 0;
            }
        }
    }

    public void setValue(int iRow, int iColumn) {
        iMatrizCampo[iRow][iColumn] = 1;
    }

    public void remValue(int iRow, int iColumn) {
        iMatrizCampo[iRow][iColumn] = 0;
    }

    public int getValue(int iRow, int iColumn) {
        return iMatrizCampo[iRow][iColumn];
    }

    public boolean validaTiro(int iRow, int iColumn) {

        if (iMatrizCampo[iRow][iColumn] == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validaQtdBombas(int[][] iCampo) {

        int iContBomba = 0;

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (iCampo[i][j] == 1) {
                    iContBomba++;
                }
            }
        }

        if (iContBomba == iBombas) {
            return true;
        } else {
            return false;
        }
    }
}
