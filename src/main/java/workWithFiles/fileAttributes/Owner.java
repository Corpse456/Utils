package workWithFiles.fileAttributes;

public class Owner implements Comparable<Owner>{
    private String owner;
    private int amount;
    private Long size;

    /**
     * @param owner
     * @param amount
     * @param size
     */
    public Owner (String owner, int amount, long size) {
        this.owner = owner;
        this.amount = amount;
        this.size = size;
    }

    public String getOwner () {
        return owner;
    }

    public void setOwner (String owner) {
        this.owner = owner;
    }

    public int getAmount () {
        return amount;
    }

    public void setAmount (int amount) {
        this.amount = amount;
    }

    public Long getSize () {
        return size;
    }

    public void setSize (long size) {
        this.size = size;
    }

    @Override
    public String toString () {
        return "Owner [owner=" + owner + ", amount=" + amount + ", size=" + size / 1024 + " Kb]";
    }

    @Override
    public int hashCode () {
        return super.hashCode();
    }

    @Override
    public boolean equals (Object obj) {
        if (!(obj instanceof Owner)) return false;
        
        return this.owner.equals(((Owner) obj).getOwner());
    }

    @Override
    public int compareTo (Owner o) {
        return o.getOwner().compareTo(this.getOwner());
    }    
}
