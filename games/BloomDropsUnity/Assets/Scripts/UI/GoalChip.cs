using UnityEngine;
using UnityEngine.UI;

namespace BloomDrops.UI
{
    public class GoalChip : MonoBehaviour
    {
        public Image icon;
        public Text label;

        public void Setup(Sprite sprite, Color color, int got, int need)
        {
            if (icon != null)
            {
                icon.sprite = sprite;
                icon.color = Color.white;
            }
            if (label != null)
            {
                int shown = Mathf.Min(got, need);
                label.text = $"{shown}/{need}";
                label.color = got >= need ? new Color(1f, 1f, 1f, 0.45f) : Color.white;
            }
        }
    }
}
