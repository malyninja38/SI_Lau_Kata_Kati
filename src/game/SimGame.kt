package game


class SimGame(player1: Player, player2: Player, matrix: Array<IntArray>):GameCore(player1,player2) {

    init{
        for(i in 0 until 21){
            this.matrix[i/3][i%3] = matrix[i/3][i%3]
        }
    }

    override fun start() {
        prepareGame()
    }

}