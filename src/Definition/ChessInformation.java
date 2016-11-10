package Definition;

/**
 * Created by AUTOY on 2016/5/5.
 */
public class ChessInformation {
    public ChessType type = ChessType.BLANK;
    public Player player = Player.BLANK;
    public ChessState state = ChessState.BLANK;
    public boolean justMoved = false;
    public boolean isChosen = false;
    public boolean isVirtual = false;
    public boolean isVirtuallyChosen = false;
    public int x;
    public int y;

    public ChessInformation(ChessType type, Player player, ChessState state, int x, int y) {
        this.type = type;
        this.player = player;
        this.state = state;
        this.x = x;
        this.y = y;
    }

    public void setInformation(ChessType type, Player player, ChessState state, int x, int y) {
        this.type = type;
        this.player = player;
        this.state = state;
        this.x = x;
        this.y = y;
    }

    public void setInformation(ChessType type, Player player, ChessState state) {
        this.type = type;
        this.player = player;
        this.state = state;
    }

    public void setInformation(ChessInformation information) {
        this.type = information.type;
        this.player = information.player;
        this.state = information.state;
        this.x = information.x;
        this.y = information.y;
    }


}
