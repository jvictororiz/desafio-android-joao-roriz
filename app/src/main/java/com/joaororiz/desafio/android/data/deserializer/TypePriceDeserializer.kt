package com.joaororiz.desafio.android.data.deserializer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.joaororiz.desafio.android.data.entities.TypePrice
import java.lang.reflect.Type

/** Deserializa os tipos que retornam do mainframe como 'S' ou 'N' para [Boolean] */
class TypePriceDeserializer : JsonDeserializer<TypePrice> {

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): TypePrice? {
        return TypePrice.values().find { it.code == json?.asString }
    }

    companion object {
        private const val SIM = "S"
        private const val ATIVO = "1"
    }

}
