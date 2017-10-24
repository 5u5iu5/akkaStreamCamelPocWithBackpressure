package poc.akkastream.protocol

sealed trait ProtocolBackPressureACK

case object ACK extends ProtocolBackPressureACK
case object INITMESSAGE extends ProtocolBackPressureACK
case object ONCOMPLETE extends ProtocolBackPressureACK

