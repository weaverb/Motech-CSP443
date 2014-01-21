package motech.database.examples;

public class Actor{
	
	private int ActorId;
	private String ActorName;
	
	public int getActorId() {
		return ActorId;
	}
	public void setActorId(int actorId) {
		ActorId = actorId;
	}
	
	public String getActorName() {
		return ActorName;
	}
	public void setActorName(String actorName) {
		ActorName = actorName;
	}
	
	@Override
	public String toString(){
		return ActorName;
	}
	
}