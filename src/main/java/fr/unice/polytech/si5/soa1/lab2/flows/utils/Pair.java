package fr.unice.polytech.si5.soa1.lab2.flows.utils;

import java.io.Serializable;

/**
 * Created by camille on 25/10/15.
 */
public class Pair<L,R> implements Serializable{

    protected L left;
    protected R right;

    public Pair(){
        this.left = null;
        this.right = null;
    }

    public Pair(L left, R right){
        this.right = right;
        this.left = left;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pair)) return false;

        Pair<?, ?> pair = (Pair<?, ?>) o;

        if (getLeft() != null ? !getLeft().equals(pair.getLeft()) : pair.getLeft() != null) return false;
        return !(getRight() != null ? !getRight().equals(pair.getRight()) : pair.getRight() != null);

    }

    @Override
    public int hashCode() {
        int result = getLeft() != null ? getLeft().hashCode() : 0;
        result = 31 * result + (getRight() != null ? getRight().hashCode() : 0);
        return result;
    }

    public L getLeft() {
        return left;
    }

    public void setLeft(L left) {
        this.left = left;
    }

    public R getRight() {
        return right;
    }

    public void setRight(R right) {
        this.right = right;
    }
}
