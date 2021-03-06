package com.yapp.giljob.domain.quest.domain

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import com.yapp.giljob.domain.position.domain.Position
import com.yapp.giljob.domain.quest.dto.request.QuestSaveRequestDto
import com.yapp.giljob.domain.subquest.domain.SubQuest
import com.yapp.giljob.domain.tag.domain.QuestTag
import com.yapp.giljob.domain.user.domain.User
import com.yapp.giljob.global.common.domain.BaseEntity
import javax.persistence.*

@Table(name = "quest")
@Entity
class Quest(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quest_id")
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "writer_id")
    var user: User,

    @Enumerated(EnumType.STRING)
    @Column(name = "position")
    var position: Position,

    @Column(name = "is_real_quest")
    var isRealQuest: Boolean = true,

    @Column
    var name: String,

    @Column
    var difficulty: Int = 0,

    @Column
    var thumbnail: String? = null,

    @Lob
    @Column(columnDefinition = "BLOB")
    var detail: String = "",

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "quest")
    var subQuestList: MutableList<SubQuest> = mutableListOf(),

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "quest")
    var tagList: MutableList<QuestTag> = mutableListOf(),
) : BaseEntity() {
    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)

    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)

    companion object {
        private val equalsAndHashCodeProperties = arrayOf(Quest::id)

        fun of(questSaveRequestDto: QuestSaveRequestDto, user: User): Quest {
            return Quest(
                user = user,
                name = questSaveRequestDto.name,
                position = questSaveRequestDto.position,
                difficulty = questSaveRequestDto.difficulty,
                thumbnail = questSaveRequestDto.thumbnail,
                detail = questSaveRequestDto.detail
            )
        }
    }
}