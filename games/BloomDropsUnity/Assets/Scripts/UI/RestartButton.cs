using UnityEngine;
using UnityEngine.UI;
using BloomDrops.Core;

namespace BloomDrops.UI
{
    public class RestartButton : MonoBehaviour
    {
        public GameManager gameManager;
        public Button button;

        void Awake()
        {
            if (button == null) button = GetComponent<Button>();
            if (button != null) button.onClick.AddListener(() => gameManager?.RestartLevel());
        }
    }
}
