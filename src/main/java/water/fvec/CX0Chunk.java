package water.fvec;

import water.*;

import java.util.Arrays;
import java.util.Iterator;

/** specialized subtype of SPARSE chunk for boolean (bitvector); no NAs.  contains just a list of rows that are non-zero. */
public final class CX0Chunk extends CXIChunk {
  // Sparse constructor
  protected CX0Chunk(int len, int nzs, byte [] buf){super(len,nzs,0,buf);}

  @Override protected final long at8_impl(int idx) {return getId(findOffset(idx)) == idx?1:0;}
  @Override protected final double atd_impl(int idx) { return at8_impl(idx); }
  @Override protected final boolean isNA_impl( int i ) { return false; }

  @Override boolean hasFloat () { return false; }

  @Override NewChunk inflate_impl(NewChunk nc) {
    final int len = sparseLen();
    nc._ls = MemoryManager.malloc8 (len);
    Arrays.fill(nc._ls,1);
    nc._xs = MemoryManager.malloc4 (len);
    nc._id = nonzeros();
    return nc;
  }

  public Iterator<Value> values(){
    return new SparseIterator(new Value(){
      @Override public final long asLong(){return 1;}
      @Override public final double asDouble() { return 1;}
      @Override public final boolean isNA(){ return false;}
    });
  }
}
