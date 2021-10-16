package com.example.gomoku;

import java.util.ArrayList;
import java.util.Scanner;

public class GameEngine {
    private int[][] gameBoard  ;
    String turn = "human";
    int plusInfinity = 200, minusInfinity = -200 ;

    int row = 8 ;
    int column = 8 ;

    int gamePoint = 5 ;
    int terminalValue = 0 ;

    int overallDepth =  5;

    int range = 2 ;

    int gameOverLine[] = new int[4] ;

    ArrayList<ArrayList<Integer>> mainBoardAvailableNodeList;

    GameEngine(){
        gameBoard = new int[row][column] ;
        mainBoardAvailableNodeList = new ArrayList<>() ;

        for(int i=0; i<row ;i++){
            for(int j=0; j<column ;j++){
                gameBoard[i][j] = 0 ;
            }
        }
    }

    public boolean AI_Turn(){
        mini_max(gameBoard, 0, "AI", minusInfinity, plusInfinity) ;
        return true;
    }

    public boolean humanTurn(int row, int column){
        gameBoard[row][column] = 6 ;

        mainBoardAvailableNodeList.clear();
        mainBoardAvailableNodeList = findAvailableNodes(gameBoard, "main") ;

        if(mainBoardAvailableNodeList.size()==0){
            mainBoardAvailableNodeList = findAvailableNodes(gameBoard, "main2") ;

            if(mainBoardAvailableNodeList.size()==0){
                return false;
            }

            if(mainBoardAvailableNodeList.size()>=20 && mainBoardAvailableNodeList.size()<30){
                overallDepth = 4 ;
            }
            else if(mainBoardAvailableNodeList.size()>=30 && mainBoardAvailableNodeList.size()<35){
                overallDepth = 4 ;
            }
            else if(mainBoardAvailableNodeList.size()>=35 && mainBoardAvailableNodeList.size()<40){
                overallDepth = 3 ;
            }
            else if(mainBoardAvailableNodeList.size()>=40  && mainBoardAvailableNodeList.size()<45){
                overallDepth = 3 ;
            }
            else if(mainBoardAvailableNodeList.size()>=45 && mainBoardAvailableNodeList.size()<50){
                overallDepth = 3 ;
            }
            else if(mainBoardAvailableNodeList.size()>=50 && mainBoardAvailableNodeList.size()<55){
                overallDepth = 3 ;
            }
            else if(mainBoardAvailableNodeList.size()>=50 && mainBoardAvailableNodeList.size()<55){
                overallDepth = 2 ;
            }
            else if(mainBoardAvailableNodeList.size()>=55 && mainBoardAvailableNodeList.size()<60){
                overallDepth = 1 ;
            }
            else if(mainBoardAvailableNodeList.size()>=60){
                overallDepth = 1 ;
            }
        }

        return true;
    }

    public int[][] getGameBoard(){
        return gameBoard ;
    }

    int setTerminalValue(int human_Count, int AI_Count, String player, int flag){
        if(flag==1){
            if(player=="human"){
                if(AI_Count>terminalValue){
                    terminalValue = AI_Count ;
                }
            }
            else if(player=="AI"){
                if(human_Count>terminalValue){
                    terminalValue = human_Count ;
                }
            }
        }
        else if(flag==2){
            if(player=="human"){
                if(AI_Count>terminalValue){
                    terminalValue = AI_Count ;
                }
            }
        }
        else if (flag==3){
            if(player=="AI"){
                if(human_Count>terminalValue){
                    terminalValue = human_Count ;
                }
            }
        }

        return terminalValue ;
    }

    public boolean changePlayer(){
        if(turn == "human"){
            turn = "AI" ;
        }
        else{
            turn = "human" ;
        }

        return true ;
    }

    boolean gameOver(int [][] board, String player){
        int human_Count=0, AI_Count=0, terminalValue=0;
        for(int i=0; i<row ; i++){
            setTerminalValue(human_Count, AI_Count, player, 1) ;
            human_Count = 0;
            AI_Count = 0;
            for(int j=0; j<column ; j++){
                if (board[i][j]==0){
                    setTerminalValue(human_Count, AI_Count, player, 1) ;
                    human_Count = 0;
                    AI_Count = 0;
                    continue;
                }

                if(board[i][j]==6){
                    setTerminalValue(human_Count, AI_Count, player, 2) ;
                    human_Count++ ;
                    AI_Count = 0 ;
                }
                else if(board[i][j]==7){
                    setTerminalValue(human_Count, AI_Count, player, 3) ;
                    human_Count=0 ;
                    AI_Count++ ;
                }

                if(human_Count==gamePoint){
                    if(player=="main"){
                        System.out.println("Human Win 1");
                        gameOverLine[0]= i ;
                        gameOverLine[1]= j-4 ;
                        gameOverLine[2]= i ;
                        gameOverLine[3]= j ;
                    }
                    return true;
                }
                else if(AI_Count == gamePoint){
                    if(player=="main"){
                        System.out.println("AI Win 1 ok");
                        gameOverLine[0]= i ;
                        gameOverLine[1]= j-4 ;
                        gameOverLine[2]= i ;
                        gameOverLine[3]= j ;
                    }
                    return true;
                }
            }
        }

        human_Count = 0;
        AI_Count = 0;
        for(int i=0; i<row ; i++){
            setTerminalValue(human_Count, AI_Count, player, 1) ;
            human_Count = 0;
            AI_Count = 0;
            for(int j=0; j<column ; j++){
                if (board[j][i]==0){
                    setTerminalValue(human_Count, AI_Count, player, 1) ;
                    human_Count = 0;
                    AI_Count = 0;
                    continue;
                }

                if(board[j][i]==6){
                    setTerminalValue(human_Count, AI_Count, player, 2) ;
                    human_Count++ ;
                    AI_Count = 0 ;
                }
                else if(board[j][i]==7){
                    setTerminalValue(human_Count, AI_Count, player, 3) ;
                    human_Count=0 ;
                    AI_Count++ ;
                }

                if(human_Count == gamePoint){
                    if(player=="main"){
                        System.out.println("Human Win 2");
                        gameOverLine[0]= j-4 ;
                        gameOverLine[1]= i ;
                        gameOverLine[2]= j ;
                        gameOverLine[3]= i ;
                    }
                    return true;
                }
                else if(AI_Count == gamePoint){
                    if(player=="main"){
                        System.out.println("AI Win 2 ok");
                        gameOverLine[0]= j-4 ;
                        gameOverLine[1]= i ;
                        gameOverLine[2]= j ;
                        gameOverLine[3]= i ;
                    }
                    return true;
                }
            }
        }

        int i = 0, j = 0;
        boolean isUp = true;
        int flag = 1 ;

        for (int k = 0; k < row * column && flag==1;) {
            setTerminalValue(human_Count, AI_Count, player, 1) ;
            human_Count = 0;
            AI_Count = 0;
            if (isUp) {
                for (; i >= 0 && j < column && i<row; j++, i--) {
                    if (board[i][j]==0){
                        setTerminalValue(human_Count, AI_Count, player, 1) ;
                        human_Count = 0;
                        AI_Count = 0;
                        if(i>=row-1&&j>=column-1) {
                            flag = -1;
                            break;
                        }
                        continue;
                    }

                    if(board[j][i]==6){
                        setTerminalValue(human_Count, AI_Count, player, 2) ;
                        human_Count++ ;
                        AI_Count = 0 ;
                    }
                    else if(board[j][i]==7){
                        setTerminalValue(human_Count, AI_Count, player, 3) ;
                        human_Count=0 ;
                        AI_Count++ ;
                    }

                    if(human_Count==gamePoint){
                        if(player=="main"){
                            System.out.println("Human Win 3");
                            gameOverLine[0]= j-4 ;
                            gameOverLine[1]= i+4 ;
                            gameOverLine[2]= j ;
                            gameOverLine[3]= i ;
                        }
                        return true;
                    }
                    else if(AI_Count == gamePoint){
                        if(player=="main"){
                            gameOverLine[0]= j-4 ;
                            gameOverLine[1]= i+4 ;
                            gameOverLine[2]= j ;
                            gameOverLine[3]= i ;
                            System.out.println("AI Win 3 ok");
                        }
                        return true;
                    }

                    k++;

                    if(i>=row-1&&j>=column-1) {
                        flag = -1;
                        break;
                    }
                }

                if (i < 0 && j <= column - 1)
                    i = 0;
                if (j == column) {
                    i = i + 2;
                    j--;
                }
            }
            else {
                for (; j >= 0 && i < row && j<column; i++, j--) {

                    if (board[i][j]==0){
                        setTerminalValue(human_Count, AI_Count, player, 1) ;
                        human_Count = 0;
                        AI_Count = 0;
                        if(i>=row-1 && j>=column-1) {
                            flag = -1;
                            break;
                        }
                        continue;
                    }

                    if(board[i][j]==6){
                        setTerminalValue(human_Count, AI_Count, player, 2) ;
                        human_Count++ ;
                        AI_Count = 0 ;
                    }
                    else if(board[i][j]==7){
                        setTerminalValue(human_Count, AI_Count, player, 3) ;
                        human_Count=0 ;
                        AI_Count++ ;
                    }

                    if(human_Count==gamePoint){
                        if(player=="main"){
                            gameOverLine[0]= i-4 ;
                            gameOverLine[1]= j+4 ;
                            gameOverLine[2]= i ;
                            gameOverLine[3]= j ;
                            System.out.println("Human Win 4");
                        }
                        return true;
                    }
                    else if(AI_Count == gamePoint){
                        if(player=="main") {
                            gameOverLine[0]= i-4 ;
                            gameOverLine[1]= j+4 ;
                            gameOverLine[2]= i ;
                            gameOverLine[3]= j ;
                            System.out.println("AI Win 4 ok");
                        }
                        return true;
                    }

                    k++;

                    if(i>=row-1&&j>=column-1) {
                        flag = -1;
                        break;
                    }
                }

                if (j < 0 && i <= row - 1)
                    j = 0;
                if (i == row) {
                    j = j + 2;
                    i--;
                }
            }
            isUp = !isUp;
        }

        i = 0;
        j = column-1;
        isUp = true;

        for (int k = 0; k < row * column;) {
            setTerminalValue(human_Count, AI_Count, player, 1) ;
            human_Count = 0;
            AI_Count = 0;
            if (isUp) {
                for (; i >= 0 && j >= 0 && i<row && j<column; j--, i--) {
                    if (board[i][j]==0){
                        setTerminalValue(human_Count, AI_Count, player, 1) ;
                        human_Count = 0;
                        AI_Count = 0;
                        if(i>=row-1&&j<=0) return false ;
                        continue;
                    }

                    if(board[i][j]==6){
                        setTerminalValue(human_Count, AI_Count, player, 2) ;
                        human_Count++ ;
                        AI_Count = 0 ;
                    }
                    else if(board[i][j]==7){
                        setTerminalValue(human_Count, AI_Count, player, 3) ;
                        human_Count=0 ;
                        AI_Count++ ;
                    }

                    if(human_Count==gamePoint){
                        if(player=="main") {
                            gameOverLine[0]= i+4 ;
                            gameOverLine[1]= j+4 ;
                            gameOverLine[2]= i ;
                            gameOverLine[3]= j ;
                            System.out.println("Human Win 5");
                        }
                        return true;
                    }
                    else if(AI_Count == gamePoint){
                        if(player=="main") {
                            System.out.println("AI Win 5 ok");
                            gameOverLine[0]= i+4 ;
                            gameOverLine[1]= j+4 ;
                            gameOverLine[2]= i ;
                            gameOverLine[3]= j ;
                        }

                        return true;
                    }

                    k++;

                    if(i>=row-1&&j<=0) return false ;
                }

                if (i < 0 && j >=0)
                    i = 0;
                if (j == -1) {
                    i = i + 2;
                    j++;
                }
            }
            else {
                for (; j >= 0 && i < row && j<column; i++, j++) {
                    if (board[i][j]==0){
                        setTerminalValue(human_Count, AI_Count, player, 1) ;
                        human_Count = 0;
                        AI_Count = 0;
                        if(i>=row-1&&j>=column-1) return false ;
                        continue;
                    }

                    if(board[i][j]==6){
                        setTerminalValue(human_Count, AI_Count, player, 2) ;
                        human_Count++ ;
                        AI_Count = 0 ;
                    }
                    else if(board[i][j]==7){
                        setTerminalValue(human_Count, AI_Count, player, 3) ;
                        human_Count=0 ;
                        AI_Count++ ;
                    }

                    if(human_Count==gamePoint) {
                        if (player == "main"){
                        System.out.println("Human Win 6 ok");
                            gameOverLine[0]= i-4 ;
                            gameOverLine[1]= j-4 ;
                            gameOverLine[2]= i ;
                            gameOverLine[3]= j ;
                        }
                        return true;
                    }
                    else if(AI_Count == gamePoint){
                        if(player=="main"){
                            System.out.println("AI Win 6 ok");
                            gameOverLine[0]= i-4 ;
                            gameOverLine[1]= j-4 ;
                            gameOverLine[2]= i ;
                            gameOverLine[3]= j ;
                        }
                        return true;
                    }

                    k++;

                    if(i>=row-1&&j<=0) return false ;
                }

                if (j >= column && i <= row - 1)
                    j = column-1;
                if (i == row) {
                    j = j - 2;
                    i--;
                }
            }
            isUp = !isUp;
        }

        return false;
    }

    int mini_max(int [][] board, int level, String player, int alpha, int beta){
        if(gameOver(board, player)){
            if(player=="human") {
                return 10;
            }
            else if(player=="AI") {
                return -10;
            }
        }

        if(level>overallDepth) {
            if(player=="AI"){
                return terminalValue  ;
            }
            else{
                return terminalValue*(-1);
            }
        }

        ArrayList<ArrayList<Integer>> availableNodeList = findAvailableNodes(board, "recursion") ;
        if(availableNodeList.size()==0){
            if(player=="AI"){
                return terminalValue  ;
            }
            else{
                return terminalValue*(-1);
            }
        }


        if(player=="AI"){
            for(int i=0 ; i<availableNodeList.size() ; i++){
                int tempBoard[][] = new int[row][column] ;

                for(int k=0; k<row ; k++){
                    for (int l=0; l<column ;l++){
                        tempBoard[k][l] = board[k][l] ;
                    }
                }

                //if(level>=overallDepth) return 0 ;

                tempBoard[availableNodeList.get(i).get(0)][availableNodeList.get(i).get(1)] = 7  ;
                alpha = max(alpha, mini_max(tempBoard ,level+1, "human", alpha, beta)) ;
                if(alpha>=beta){
                    break;
                }
                if(level==0){
                    //System.out.println(i + "===========================================>>:"+ alpha);
                    availableNodeList.get(i).add(alpha) ;
                    //alpha = minusInfinity ;
                }
            }
            if(level==0){
                int index = 0 ;
                int max = availableNodeList.get(0).get(2) ;
                for(int i=1 ; i<availableNodeList.size() ; i++){
                    if(availableNodeList.get(i).get(2)>max){
                        max = availableNodeList.get(i).get(2) ;
                        index = i ;
                    }
                }
                if(level>=overallDepth) return 0 ;
                /*for(int m=0 ; m<availableNodeList.size(); m++){
                    System.out.println(availableNodeList.get(m).get(0) + "\t" + availableNodeList.get(m).get(1) + "\t" + availableNodeList.get(m).get(2));
                }*/
                gameBoard[availableNodeList.get(index).get(0)][availableNodeList.get(index).get(1)] = 7;
            }

            //System.out.println("Level:" + level + "\tAlpha" + alpha + "\tBeta:" + beta);
            //scanner.nextInt();
            return alpha ;
        }
        else{
            for(int i=0 ; i<availableNodeList.size() ; i++){
                int tempBoard[][] = new int[row][column] ;

                for(int k=0; k<row ; k++){
                    for (int l=0; l<column ;l++){
                        tempBoard[k][l] = board[k][l] ;
                    }
                }
                tempBoard[availableNodeList.get(i).get(0)][availableNodeList.get(i).get(1)] = 6  ;
                int mini_max_return = mini_max(tempBoard, level+1, "AI", alpha, beta) ;
                beta = min(beta, mini_max_return) ;
                if(alpha>=beta){
                    //System.out.println("Alpha:"+alpha+"\nBeta:"+beta);
                    break;
                }
            }
            //System.out.println("/////////////////////////"+ beta);
            //System.out.println("LevelHumanOdd:" + level + "\tAlpha" + alpha + "\tBeta:" + beta);
            //scanner.nextInt();
            return beta ;
        }
    }

    ArrayList<ArrayList<Integer>> findAvailableNodes(int [][] board, String flagForArrayList){
        ArrayList<ArrayList<Integer>> availableNodeList = new ArrayList<>() ;
        for(int i=0 ; i<row ; i++){
            for (int j=0 ; j<column ; j++){
                if (board[i][j]==0){
                    ArrayList<Integer> arrayList = new ArrayList<>(3) ;
                    arrayList.add(i) ;
                    arrayList.add(j) ;

                    if(flagForArrayList!="main" && flagForArrayList!="main2"){
                        if(!mainBoardAvailableNodeList.contains(arrayList)) continue;
                    }

                    int importantPointFlag = -1 ;

                    if(flagForArrayList=="main"){
                        importantPointFlag = checkImportantAvailableNode(board, i, j) ;
                    }

                    if(flagForArrayList!="main"){
                        importantPointFlag = -1 ;
                        for(int k=i-range; k<=i+range ; k++){
                            for(int l=j-range; l<=j+range ; l++){
                                if(k==i&&l==j || k<0 || l<0 || k>=row || l>=column){
                                    continue;
                                }
                                if(board[k][l]!=0){
                                    importantPointFlag = 1 ;
                                    break;
                                }
                            }
                            if(importantPointFlag==1) break;
                        }
                    }

                    if(importantPointFlag==1){
                        availableNodeList.add(arrayList) ;
                    }
                }
            }
        }

        return availableNodeList ;
    }

    int checkImportantAvailableNode(int board[][], int i, int j){
        int leftAI = 0, leftHuman = 0, rightAI = 0, rightHuman = 0, topAI = 0 , topHuman = 0, bottomAI = 0, bottomHuman = 0 ;
        int topLeftAI = 0, topRightAI = 0, bottomLeftAI = 0, bottomRightAI = 0 , topLeftHuman = 0, topRightHuman = 0, bottomLeftHuman = 0, bottomRightHuman = 0 ;

        for(int k=j-1 ; k>=0 ; k--){
            if(k==j-1){
                if(board[i][k]==6){
                    leftHuman++ ;
                    continue;
                }
                else if(board[i][k]==7){
                    leftAI++ ;
                    continue;
                }
                else break;
            }
            else{
                if(leftHuman!=0 && board[i][k]==6)
                    leftHuman++ ;

                else if(leftAI!=0 && board[i][k]==7)
                    leftAI++ ;

                else break;
            }
        }

        for(int k=j+1 ; k<column ; k++){
            if(k==j+1){
                if(board[i][k]==6){
                    rightHuman++ ;
                    continue;
                }
                else if(board[i][k]==7){
                    rightAI++ ;
                    continue;
                }
                else break;
            }
            else{
                if(rightHuman!=0 && board[i][k]==6)
                    rightHuman++ ;

                else if(rightAI!=0 && board[i][k]==7)
                    rightAI++ ;

                else break;
            }
        }

        int importantPointFlag = -1 ;

        if(leftAI+rightAI>=4 && leftAI!=0 && rightAI!=0 || leftHuman+rightHuman>=4 && leftHuman!=0 && rightHuman!=0){
            importantPointFlag = 1 ;
        }
        else if(j-leftAI-1==-1 || j+rightAI+1 == column || j-leftHuman-1 == -1 || j+rightHuman+1 == column){

        }
        else if(leftAI+rightAI>=3 && leftAI!=0 && rightAI!=0 && board[i][j-leftAI-1]==0 && board[i][j+rightAI+1]==0
                || leftHuman+rightHuman>=3 && leftHuman!=0 && rightHuman!=0 && board[i][j-leftHuman-1]==0 && board[i][j+rightHuman+1]==0){
            importantPointFlag = 1 ;
        }
        else if(leftAI==4 || rightAI==4 || leftHuman==4 || rightHuman==4){
            importantPointFlag = 1 ;
        }
        else if(leftAI>=3 && rightAI==0 && rightHuman==0){
            if(leftAI==3 && j<column-1){
                importantPointFlag = 1 ;
            }
            else if(leftAI==4 && j<column){
                importantPointFlag = 1 ;
            }
        }
        else if(leftHuman>=3 && rightAI==0 && rightHuman==0){
            if(leftHuman==3 && j<column-1){
                importantPointFlag = 1 ;
            }
            else if(leftHuman==4 && j<column){
                importantPointFlag = 1 ;
            }
        }
        else if(rightAI>=3 && leftAI==0 && leftHuman==0){
            if(rightAI==3 && j>=1){
                importantPointFlag = 1 ;
            }
            else if(rightAI==4 && j>=0){
                importantPointFlag = 1 ;
            }
        }
        else if(rightHuman>=3 && leftAI==0 && leftHuman==0){
            if(rightHuman==3 && j>=1){
                importantPointFlag = 1 ;
            }
            else if(rightHuman==4 && j>=0){
                importantPointFlag = 1 ;
            }
        }

        if(importantPointFlag==-1){
            for(int k=i-1 ; k>=0 ; k--){
                if(k==i-1){
                    if(board[k][j]==6){
                        topHuman++ ;
                        continue;
                    }
                    else if(board[k][j]==7){
                        topAI++ ;
                        continue;
                    }
                    else break;
                }
                else{
                    if(topHuman!=0 && board[k][j]==6)
                        topHuman++ ;

                    else if(topAI!=0 && board[k][j]==7)
                        topAI++ ;

                    else break;
                }
            }

            for(int k=i+1 ; k<row ; k++){
                if(k==i+1){
                    if(board[k][j]==6){
                        bottomHuman++ ;
                        continue;
                    }
                    else if(board[k][j]==7){
                        bottomAI++ ;
                        continue;
                    }
                    else break;
                }
                else{
                    if(bottomHuman!=0 && board[k][j]==6)
                        bottomHuman++ ;

                    else if(bottomAI!=0 && board[k][j]==7)
                        bottomAI++ ;

                    else break;
                }
            }

            if(topAI+bottomAI>=4 && topAI!=0 && bottomAI!=0 || topHuman+bottomHuman>=4 && topHuman!=0 && bottomHuman!=0){
                importantPointFlag = 1 ;
            }
            else if(i-topAI-1==-1 || i-topHuman-1 ==-1 || i+bottomAI+1==column || i-topHuman-1==-1 || i+bottomHuman+1==column){}
            else if(topAI+bottomAI>=3 && topAI!=0 && bottomAI!=0 && board[i-topAI-1][j]==0 && board[i+bottomAI+1][j]==0
                    || topHuman+bottomHuman>=3 && topHuman!=0 && bottomHuman!=0 && board[i-topHuman-1][j]==0 && board[i+bottomHuman+1][j]==0){
                importantPointFlag = 1 ;
            }
            else if(topAI==4 || topHuman==4 || bottomAI==4 || bottomHuman==4){
                importantPointFlag = 1 ;
            }
            else if(topAI>=3 && bottomAI==0 && bottomHuman==0){
                if(topAI==3 && i<row-1){
                    importantPointFlag = 1 ;
                }
                else if(topAI==4 && i<row){
                    importantPointFlag = 1 ;
                }
            }
            else if(topHuman>=3 && bottomAI==0 && bottomHuman==0){
                if(topHuman==3 && i<row-1){
                    importantPointFlag = 1 ;
                }
                else if(topHuman==4 && i<row){
                    importantPointFlag = 1 ;
                }
            }
            else if(bottomAI>=3 && topAI==0 && topHuman==0){
                if(bottomAI==3 && i>=1){
                    importantPointFlag = 1 ;
                }
                else if(bottomAI==4 && i>=0){
                    importantPointFlag = 1 ;
                }

            }
            else if(bottomHuman>=3 && topAI==0 && topHuman==0){
                if(bottomHuman==3 && i>=1){
                    importantPointFlag = 1 ;
                }
                else if(bottomHuman==4 && i>=0){
                    importantPointFlag = 1 ;
                }
            }
        }

        if(importantPointFlag==-1){
            for(int k=i-1, l=j-1 ; k>=0 && l>=0 ; k--, l--){
                if(k==i-1 && l==j-1){
                    if(board[k][l]==6){
                        topLeftHuman++ ;
                        continue;
                    }
                    else if(board[k][l]==7){
                        topLeftAI++ ;
                        continue;
                    }
                    else break;
                }
                else{
                    if(topLeftHuman!=0 && board[k][l]==6)
                        topLeftHuman++ ;

                    else if(topLeftAI!=0 && board[k][l]==7)
                        topLeftAI++ ;

                    else break;
                }
            }

            for(int k=i+1, l=j+1 ; k<row && l<column ; k++, l++){
                if(k==i+1 && l==j+1){
                    if(board[k][l]==6){
                        bottomRightHuman++ ;
                        continue;
                    }
                    else if(board[k][l]==7){
                        bottomRightAI++ ;
                        continue;
                    }
                    else break;
                }
                else{
                    if(bottomRightHuman!=0 && board[k][l]==6)
                        bottomRightHuman++ ;

                    else if(bottomRightAI!=0 && board[k][l]==7)
                        bottomRightAI++ ;

                    else break;
                }
            }

            //System.out.println("value i:" + i + "\t\tvalue j:" + j + "\t\thumanTopLEft:" + topLeftHuman + "\t\tbotttomRight" + bottomRightHuman );

            if(topLeftAI+bottomRightAI>=4 && topLeftAI!=0 && bottomRightAI!=0 || topLeftHuman+bottomRightHuman>=4 && topLeftHuman!=0 && bottomRightHuman!=0){
                importantPointFlag = 1 ;
            }
            else if(i-topLeftAI-1==-1 || j-topLeftAI-1==-1 || i+bottomRightAI+1 == column || j+bottomRightAI+1 == column || i-topLeftHuman-1 ==-1 || j-topLeftHuman-1 ==-1 || i+bottomRightHuman+1 == column || j+bottomRightHuman+1==column){

            }
            else if(topLeftAI+bottomRightAI>=3 && topLeftAI!=0 && bottomRightAI!=0 && board[i-topLeftAI-1][j-topLeftAI-1]==0 && board[i+bottomRightAI+1][j+bottomRightAI+1]==0
                    || topLeftHuman+bottomRightHuman>=3 && topLeftHuman!=0 && bottomRightHuman!=0 && board[i-topLeftHuman-1][j-topLeftHuman-1]==0 && board[i+bottomRightHuman+1][j+bottomRightHuman+1]==0){
                importantPointFlag = 1 ;
            }
            else if(topLeftAI==4 || bottomRightAI==4 || topLeftHuman==4 || bottomRightHuman==4){
                importantPointFlag = 1 ;
            }
            else if(topLeftAI>=3 && bottomRightAI==0 && bottomRightHuman==0){
                if(topLeftAI==3 && j<column-1){
                    importantPointFlag = 1 ;
                }
                else if(topLeftAI==4 && j<column){
                    importantPointFlag = 1 ;
                }
            }
            else if(topLeftHuman>=3 && bottomRightAI==0 && bottomRightHuman==0){
                if(topLeftHuman==3 && j<column-1){
                    importantPointFlag = 1 ;
                }
                else if(topLeftHuman==4 && j<column){
                    importantPointFlag = 1 ;
                }
            }
            else if(bottomRightAI>=3 && topLeftAI==0 && topLeftHuman==0){
                if(bottomRightAI==3 && j>=1){
                    importantPointFlag = 1 ;
                }
                else if(bottomRightAI==4 && j>=0){
                    importantPointFlag = 1 ;
                }
            }
            else if(bottomRightHuman>=3 && topLeftAI==0 && topLeftHuman==0){
                if(bottomRightHuman==3 && j>=1){
                    importantPointFlag = 1 ;
                }
                else if(bottomRightHuman==4 && j>=0){
                    importantPointFlag = 1 ;
                }
            }
        }

        if(importantPointFlag==-1){
            for(int k=i+1, l=j-1 ; k<row && l>=0 ; k++, l--){
                if(k==i+1 && l==j-1){
                    if(board[k][l]==6){
                        bottomLeftHuman++ ;
                        continue;
                    }
                    else if(board[k][l]==7){
                        bottomLeftAI++ ;
                        continue;
                    }
                    else break;
                }
                else{
                    if(bottomLeftHuman!=0 && board[k][l]==6)
                        bottomLeftHuman++ ;

                    else if(bottomLeftAI!=0 && board[k][l]==7)
                        bottomLeftAI++ ;

                    else break;
                }
            }

            for(int k=i-1, l=j+1 ; k>=0 && l<column ; k--, l++){
                if(k==i-1 && l==j+1){
                    if(board[k][l]==6){
                        topRightHuman++ ;
                        continue;
                    }
                    else if(board[k][l]==7){
                        topRightAI++ ;
                        continue;
                    }
                    else break;
                }
                else{
                    if(topRightHuman!=0 && board[k][l]==6) {
                        topRightHuman++;
                    }

                    else if(topRightAI!=0 && board[k][l]==7){
                        topRightAI++ ;
                    }

                    else break;
                }
            }

            if(bottomLeftAI+topRightAI>=4 && bottomLeftAI!=0 && topRightAI!=0){
                importantPointFlag = 1 ;
            }
            else if(i+bottomLeftAI+1==column || j-bottomLeftAI-1==-1 || i-topRightAI-1==-1 || j+topRightAI+1==column || j+topRightHuman+1==column || i-topRightHuman-1==-1 || j-bottomLeftHuman-1==-1 || i+bottomLeftHuman+1==column){}
            else if(bottomLeftAI+topRightAI>=3 && bottomLeftAI!=0 && topRightAI!=0 && board[i+bottomLeftAI+1][j-bottomLeftAI-1]==0 && board[i-topRightAI-1][j+topRightAI+1]==0
                    || bottomLeftHuman+topRightHuman>=4 && bottomLeftHuman!=0 && topRightHuman!=0 && board[i+bottomLeftHuman+1][j-bottomLeftHuman-1]==0 && board[i-topRightHuman-1][j+topRightHuman+1]==0 ){
                importantPointFlag = 1 ;
            }
            else if(bottomLeftAI==4 || topRightAI==4 || bottomLeftHuman==4 || topRightHuman==4){
                importantPointFlag = 1 ;
            }
            else if(bottomLeftAI>=3 && topRightAI==0 && topRightHuman==0){
                if(bottomLeftAI==3 && j<column-1){
                    importantPointFlag = 1 ;
                }
                else if(bottomLeftAI==4 && j<column){
                    importantPointFlag = 1 ;
                }
            }
            else if(bottomLeftHuman>=3 && topRightAI==0 && topRightHuman==0){
                if(bottomLeftHuman==3 && j<column-1){
                    importantPointFlag = 1 ;
                }
                else if(bottomLeftHuman==4 && j<column){
                    importantPointFlag = 1 ;
                }
            }
            else if(topRightAI>=3 && bottomLeftAI==0 && bottomLeftHuman==0){
                if(topRightAI==3 && j>=1){
                    importantPointFlag = 1 ;
                }
                else if(topRightAI==4 && j>=0){
                    importantPointFlag = 1 ;
                }
            }
            else if(topRightHuman>=3 && bottomLeftAI==0 && bottomLeftHuman==0){
                if(topRightHuman==3 && j>=1){
                    importantPointFlag = 1 ;
                }
                else if(topRightHuman==4 && j>=0){
                    importantPointFlag = 1 ;
                }
            }
        }

        return importantPointFlag ;
    }

    int min(int a, int b){
        if(a<b) return a ;
        else return b ;
    }

    int max(int a, int b){
        if(a>b) return a ;
        else return b ;
    }
}
