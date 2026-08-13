using System;
using UnityEngine;

namespace BloomDrops.Data
{
    public enum EssenceId
    {
        Coral = 0,
        Mint = 1,
        Gold = 2,
        Sky = 3,
        Blossom = 4
    }

    [Serializable]
    public class EssenceDef
    {
        public EssenceId id;
        public string displayName;
        public Color color = Color.white;
        public Sprite sprite;
    }

    [Serializable]
    public class GoalDef
    {
        public EssenceId essence;
        public int amount = 10;
    }

    [Serializable]
    public class LevelDef
    {
        public int boardSize = 6;
        public int moves = 20;
        public int colorCount = 4;
        public GoalDef[] goals;
    }

    [CreateAssetMenu(menuName = "Bloom Drops/Essence Catalog", fileName = "EssenceCatalog")]
    public class EssenceCatalog : ScriptableObject
    {
        public EssenceDef[] essences;

        public EssenceDef Get(EssenceId id)
        {
            foreach (var e in essences)
            {
                if (e.id == id) return e;
            }
            return essences != null && essences.Length > 0 ? essences[0] : null;
        }
    }

    [CreateAssetMenu(menuName = "Bloom Drops/Level Pack", fileName = "LevelPack")]
    public class LevelPack : ScriptableObject
    {
        public LevelDef[] levels;
    }
}
