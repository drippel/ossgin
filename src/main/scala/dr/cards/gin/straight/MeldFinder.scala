package dr.cards.gin.straight

import dr.cards.model.Card

trait MeldFinder[T <: Meld] {

  def find( cards : List[Card] ) : List[T] = {
    findLoop( sort( cards ), List[T]() )
  }

  def findLoop( cards : List[Card], accum : List[T] ) : List[T] = {

    if( stop( cards ) ){
      accum
    }
    else {
      findSingle( cards ) match {

        case Some(r) => {
          val newAccum = accum :+ r
          val rest = cards.filter( (c) => { !r.cards.contains(c) } )
          findLoop( rest, newAccum )
        }
        case None => {
          findLoop( cards.tail, accum )
        }

      }
    }
  }

  def findSingle( cards : List[Card] ) : Option[T]

  def stop( cards : List[Card] ) : Boolean

  def sort( cards : List[Card] ) : List[Card]

}