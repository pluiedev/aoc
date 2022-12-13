package me.pluie.aoc.c2022

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*
import me.pluie.aoc.*

@Serializable(with = Packet.Serializer::class)
sealed interface Packet: Comparable<Packet> {
    @Serializable(with = List.Serializer::class)
    @JvmInline
    value class List(val v: kotlin.collections.List<Packet>): Packet {
        constructor(vararg pkts: Packet) : this(listOf(*pkts))

        override fun compareTo(other: Packet): kotlin.Int = when (other) {
            is List -> v.zip(other.v)
                .m { (a, b) -> a.compareTo(b) }
                .find { it != 0 } ?: v.size.compareTo(other.v.size)
            is Int -> compareTo(List(other))
        }
        object Serializer : KSerializer<List> {
            private val delegate = ListSerializer(Packet.serializer())
            @OptIn(ExperimentalSerializationApi::class)
            override val descriptor = SerialDescriptor("Packet.List", delegate.descriptor)
            override fun serialize(encoder: Encoder, value: List) = impossible()
            override fun deserialize(decoder: Decoder): List = List(delegate.deserialize(decoder))
        }
    }
    @Serializable
    @JvmInline
    value class Int(val v: kotlin.Int): Packet {
        override fun compareTo(other: Packet): kotlin.Int = when (other) {
            is List -> List(this).compareTo(other)
            is Int -> v.compareTo(other.v)
        }
    }
    object Serializer : JsonContentPolymorphicSerializer<Packet>(Packet::class) {
        override fun selectDeserializer(element: JsonElement) = when (element) {
            is JsonPrimitive -> Int.serializer()
            is JsonArray -> List.serializer()
            else -> impossible()
        }
    }
}

fun main() = challenge(2022, 13) {
    val packets = blocks().fm { s ->
        s.m { Json.decodeFromString<Packet>(it) }
    }
    submit {
        packets.chunked(2)
            .mapIndexed { ind, (a, b) -> ind + 1 to a.compareTo(b) }
            .filter { it.second == -1 }
            .sumOf { it.first }
    }
    val div1 = Packet.List(Packet.List(Packet.Int(2)))
    val div2 = Packet.List(Packet.List(Packet.Int(6)))
    val packs = mutableListOf<Packet>(div1, div2)
    packs.addAll(packets)
    packs.sort()

    submit { (packs.indexOf(div1) + 1) * (packs.indexOf(div2) + 1) }
}