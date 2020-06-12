package game

class InvalidMoveException : Error() {}
class CaptureRequiredException : Error() {}

enum class PlayerType {
    Human,
    AI
}

class Player(val type: PlayerType, val number: Int){}