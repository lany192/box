package cn.smallplants.client.event


class ExchangeEvent {
    var prizeId: Long = 0
    var addressId: Long = 0
    var count = 0

    constructor()
    constructor(prizeId: Long, addressId: Long, count: Int) {
        this.prizeId = prizeId
        this.addressId = addressId
        this.count = count
    }
}