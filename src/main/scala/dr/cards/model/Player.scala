package dr.cards.model

class Player( val name : String ) {

  override def equals( o : Any ) : Boolean = {

    if( o.isInstanceOf[Player]  ){
      val p = o.asInstanceOf[Player]
      return p.name.equals(name)
    }

    false
  }

}