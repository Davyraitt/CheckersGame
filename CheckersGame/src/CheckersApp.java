import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CheckersApp extends Application {
	
	public static final int TILE_SIZE = 100; // i tile is 100*100
	public static final int WIDTH = 8; // the board is 8 width
	public static final int HEIGHT = 8; // the board is 8 height
	
	private Group tileGroup = new Group (  ); //seperate groups for the tiles, Group is a collection from Javafx
	private Group pieceGroup = new Group (  ); //seperate groups for the pieces, Group is a collection from Javafx
	

	
	private Tile[][] boardTiles = new Tile[WIDTH][HEIGHT];
	
	
	private Parent createContent() {
		Pane root = new Pane();
		root.setPrefSize ( WIDTH * TILE_SIZE, HEIGHT*TILE_SIZE );
		root.getChildren ().addAll ( tileGroup, pieceGroup );
		
		//Creating our tiles
		for ( int y = 0 ; y < HEIGHT ; y++ )
		{
			for ( int x = 0 ; x < WIDTH ; x++ )
			{
				Tile tile = new Tile ( ( x + y) % 2 == 0 , x ,y );
				boardTiles[x][y] = tile;
				tileGroup.getChildren ().add ( tile );
				
				Piece piece = null;
				
				if (y <= 2 && ( x + y) % 2 != 0 ) {
						piece = makePiece(PieceType.RED, x, y);
				}
				
				if (y >= 5 && ( x + y) % 2 != 0 ) {
					piece = makePiece(PieceType.BLUE, x, y);
				}
				
				if ( piece != null )
				{
					tile.setPiece ( piece );
					pieceGroup.getChildren ().addAll ( piece );
				}
				
				
			}
			
			
		}
		
		
		return root;
	}
	
	@Override
	public void start ( Stage primaryStage ) throws Exception {
		Scene scene = new Scene (createContent ());
		primaryStage.setScene ( scene );
		primaryStage.show ();
		
	}
	
	public static void main ( String[] args ) {
		launch (args);
	}
	
	private Piece makePiece (PieceType type, int x, int y) {
		Piece piece = new Piece (type, x, y);
		
		piece.setOnMouseReleased ( e -> {
			int newX = convertToBoardCoordinates ( piece.getLayoutX ());
			int newY = convertToBoardCoordinates ( piece.getLayoutY ());
			
			MoveResult result = tryMove ( piece, newX, newY );
			
			int x0 = convertToBoardCoordinates (piece.getOldMouseX ()  );
			int y0 = convertToBoardCoordinates (piece.getOldMouseY ()  );
			
			switch ( result.getType ( ) )
			{
				case NONE:
					System.out.println ("calling none" );
					piece.cancelMove ();
					break;
				case NORMAL:
					System.out.println ("calling normal" );
					piece.move ( newX, newY );
					boardTiles[x0][y0].setPiece ( null );
					boardTiles[newX][newY].setPiece ( piece );
					break;
				case KILL:
					piece.move(newX, newY);
					boardTiles[x0][y0].setPiece(null);
					boardTiles[newX][newY].setPiece(piece);
					
					Piece otherPiece = result.getPiece();
					boardTiles[convertToBoardCoordinates(otherPiece.getOldMouseX())][convertToBoardCoordinates(otherPiece.getOldMouseY())].setPiece(null);
					pieceGroup.getChildren().remove(otherPiece);
					break;
			}
		} );
		
		return piece;
	}
	
	private MoveResult tryMove(Piece piece, int newX, int newY) {
		if (boardTiles[newX][newY].hasPiece() || (newX + newY) % 2 == 0) {
			return new MoveResult(MoveType.NONE);
		}
		
		int x0 = convertToBoardCoordinates (piece.getOldMouseX ());
		int y0 = convertToBoardCoordinates(piece.getOldMouseY());
		
		if (Math.abs(newX - x0) == 1 && newY - y0 == piece.getType().moveDir) {
			return new MoveResult(MoveType.NORMAL);
		} else if (Math.abs(newX - x0) == 2 && newY - y0 == piece.getType().moveDir * 2) {
			
			int x1 = x0 + (newX - x0) / 2;
			int y1 = y0 + (newY - y0) / 2;
			
			if (boardTiles[x1][y1].hasPiece() && boardTiles[x1][y1].getPiece().getType() != piece.getType()) {
				return new MoveResult(MoveType.KILL, boardTiles[x1][y1].getPiece());
			}
		}
		
		return new MoveResult(MoveType.NONE);
	}
	
	private int convertToBoardCoordinates (double pixel) {
		return (int) (pixel + TILE_SIZE / 2  ) / TILE_SIZE; // divided by 2 because we want it to be centered
	}
}
