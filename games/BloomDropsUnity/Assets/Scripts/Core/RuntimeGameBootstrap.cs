using System.Collections.Generic;
using BloomDrops.Data;
using BloomDrops.UI;
using UnityEngine;
using UnityEngine.EventSystems;
using UnityEngine.UI;

namespace BloomDrops.Core
{
    /// <summary>
    /// Builds the full Bloom Drops game at runtime so Play works
    /// even before the Editor setup menu is run.
    /// </summary>
    public static class RuntimeGameBootstrap
    {
        const string MarkerName = "__BloomDropsRuntime";

        [RuntimeInitializeOnLoadMethod(RuntimeInitializeLoadType.AfterSceneLoad)]
        static void Boot()
        {
            if (Object.FindObjectOfType<GameManager>() != null) return;
            if (GameObject.Find(MarkerName) != null) return;
            Build();
        }

        [RuntimeInitializeOnLoadMethod(RuntimeInitializeLoadType.SubsystemRegistration)]
        static void ResetStatics()
        {
            // Domain reload safety for Enter Play Mode options
        }

        public static void Build()
        {
            var marker = new GameObject(MarkerName);
            Object.DontDestroyOnLoad(marker);

            var catalog = BuildCatalog();
            var levels = BuildLevels();

            ConfigureCamera();
            CreateBackground();

            var dropPrefab = CreateDropPrefab(catalog.essences[0].sprite);
            var boardGo = new GameObject("Board");
            var board = boardGo.AddComponent<BoardController>();
            board.catalog = catalog;
            board.dropPrefab = dropPrefab;
            board.boardRoot = boardGo.transform;
            board.cellSize = 1.05f;

            var lineGo = new GameObject("LinkLine");
            lineGo.transform.SetParent(boardGo.transform, false);
            var lr = lineGo.AddComponent<LineRenderer>();
            lr.material = new Material(Shader.Find("Sprites/Default"));
            lr.numCapVertices = 4;
            lr.sortingOrder = 10;
            lr.startWidth = 0.12f;
            lr.endWidth = 0.12f;
            board.linkLine = lr;

            var input = boardGo.AddComponent<BoardInput>();
            input.board = board;

            var camFit = boardGo.AddComponent<CameraFit>();
            camFit.board = board;
            camFit.target = Camera.main;

            EnsureEventSystem();
            var ui = BuildUi(catalog, out var hud, out var result, out var restartBtn, out var goalChipPrefab);
            hud.goalChipPrefab = goalChipPrefab;

            var gmGo = new GameObject("GameManager");
            var gm = gmGo.AddComponent<GameManager>();
            gm.catalog = catalog;
            gm.levelPack = levels;
            gm.board = board;
            gm.input = input;
            gm.hud = hud;
            gm.resultPopup = result;

            var rb = restartBtn.AddComponent<RestartButton>();
            rb.gameManager = gm;
            rb.button = restartBtn.GetComponent<Button>();

            // Force enable hooks then start
            gm.enabled = false;
            gm.enabled = true;
        }

        static EssenceCatalog BuildCatalog()
        {
            var catalog = ScriptableObject.CreateInstance<EssenceCatalog>();
            catalog.essences = new[]
            {
                MakeEssence(EssenceId.Coral, "Coral Rose", new Color(1f, 0.42f, 0.42f), "Drops/drop-coral"),
                MakeEssence(EssenceId.Mint, "Mint Dew", new Color(0.37f, 0.92f, 0.83f), "Drops/drop-mint"),
                MakeEssence(EssenceId.Gold, "Sun Pollen", new Color(0.98f, 0.75f, 0.14f), "Drops/drop-gold"),
                MakeEssence(EssenceId.Sky, "Sky Drop", new Color(0.38f, 0.65f, 0.98f), "Drops/drop-sky"),
                MakeEssence(EssenceId.Blossom, "Night Blossom", new Color(0.98f, 0.66f, 0.83f), "Drops/drop-blossom"),
            };
            return catalog;
        }

        static EssenceDef MakeEssence(EssenceId id, string name, Color color, string resourcePath)
        {
            return new EssenceDef
            {
                id = id,
                displayName = name,
                color = color,
                sprite = LoadSprite(resourcePath, color)
            };
        }

        static Sprite LoadSprite(string resourcePath, Color fallbackColor)
        {
            var sprite = Resources.Load<Sprite>(resourcePath);
            if (sprite != null) return sprite;

            var tex = Resources.Load<Texture2D>(resourcePath);
            if (tex != null)
            {
                return Sprite.Create(
                    tex,
                    new Rect(0, 0, tex.width, tex.height),
                    new Vector2(0.5f, 0.5f),
                    100f
                );
            }

            // Procedural orb if art missing
            const int size = 128;
            var generated = new Texture2D(size, size, TextureFormat.RGBA32, false);
            var px = new Color[size * size];
            float cx = (size - 1) * 0.5f;
            for (int y = 0; y < size; y++)
            for (int x = 0; x < size; x++)
            {
                float dx = (x - cx) / cx;
                float dy = (y - cx) / cx;
                float d = Mathf.Sqrt(dx * dx + dy * dy);
                if (d > 1f) px[y * size + x] = Color.clear;
                else
                {
                    float h = Mathf.Clamp01(1f - d);
                    var c = Color.Lerp(fallbackColor * 0.65f, Color.white, h * 0.55f);
                    c.a = Mathf.SmoothStep(0f, 1f, (1f - d) * 8f);
                    px[y * size + x] = c;
                }
            }
            generated.SetPixels(px);
            generated.Apply();
            generated.filterMode = FilterMode.Bilinear;
            return Sprite.Create(generated, new Rect(0, 0, size, size), new Vector2(0.5f, 0.5f), 100f);
        }

        static LevelPack BuildLevels()
        {
            var pack = ScriptableObject.CreateInstance<LevelPack>();
            pack.levels = new[]
            {
                L(6, 22, 4, G(EssenceId.Coral, 18), G(EssenceId.Mint, 18), G(EssenceId.Gold, 12)),
                L(6, 20, 4, G(EssenceId.Coral, 20), G(EssenceId.Mint, 16), G(EssenceId.Sky, 16)),
                L(7, 24, 5, G(EssenceId.Gold, 22), G(EssenceId.Blossom, 18), G(EssenceId.Mint, 18)),
                L(7, 22, 5, G(EssenceId.Coral, 20), G(EssenceId.Sky, 20), G(EssenceId.Gold, 16), G(EssenceId.Mint, 12)),
                L(8, 26, 5, G(EssenceId.Coral, 24), G(EssenceId.Mint, 24), G(EssenceId.Gold, 20), G(EssenceId.Blossom, 16)),
            };
            return pack;
        }

        static LevelDef L(int size, int moves, int colors, params GoalDef[] goals)
        {
            return new LevelDef { boardSize = size, moves = moves, colorCount = colors, goals = goals };
        }

        static GoalDef G(EssenceId id, int amount) => new GoalDef { essence = id, amount = amount };

        static void ConfigureCamera()
        {
            var cam = Camera.main;
            if (cam == null)
            {
                var go = new GameObject("Main Camera", typeof(Camera));
                go.tag = "MainCamera";
                cam = go.GetComponent<Camera>();
            }
            cam.orthographic = true;
            cam.orthographicSize = 6.2f;
            cam.backgroundColor = new Color(0.027f, 0.102f, 0.114f);
            cam.clearFlags = CameraClearFlags.SolidColor;
            cam.transform.position = new Vector3(0f, 0.4f, -10f);
        }

        static void CreateBackground()
        {
            var bg = new GameObject("Background", typeof(SpriteRenderer));
            var sr = bg.GetComponent<SpriteRenderer>();
            sr.sprite = LoadSprite("UI/bloom-drops-bg", new Color(0.05f, 0.2f, 0.22f));
            sr.sortingOrder = -20;
            bg.transform.position = new Vector3(0f, 0f, 1f);
            bg.transform.localScale = new Vector3(1.35f, 1.35f, 1f);
        }

        static DropView CreateDropPrefab(Sprite sprite)
        {
            var go = new GameObject("Drop", typeof(SpriteRenderer), typeof(DropView));
            go.SetActive(false);
            var sr = go.GetComponent<SpriteRenderer>();
            sr.sprite = sprite;
            sr.sortingOrder = 2;
            go.transform.localScale = Vector3.one * 0.85f;
            Object.DontDestroyOnLoad(go);
            return go.GetComponent<DropView>();
        }

        static void EnsureEventSystem()
        {
            if (Object.FindObjectOfType<EventSystem>() != null) return;
            var es = new GameObject("EventSystem", typeof(EventSystem), typeof(StandaloneInputModule));
            Object.DontDestroyOnLoad(es);
        }

        static Font UiFont()
        {
            var font = Resources.GetBuiltinResource<Font>("LegacyRuntime.ttf");
            if (font == null) font = Resources.GetBuiltinResource<Font>("Arial.ttf");
            if (font == null) font = Font.CreateDynamicFontFromOSFont("Arial", 28);
            return font;
        }

        static GameObject BuildUi(
            EssenceCatalog catalog,
            out HudController hud,
            out ResultPopup result,
            out GameObject restartBtn,
            out GoalChip goalChipPrefab)
        {
            var canvasGo = new GameObject("Canvas", typeof(Canvas), typeof(CanvasScaler), typeof(GraphicRaycaster));
            var canvas = canvasGo.GetComponent<Canvas>();
            canvas.renderMode = RenderMode.ScreenSpaceOverlay;
            var scaler = canvasGo.GetComponent<CanvasScaler>();
            scaler.uiScaleMode = CanvasScaler.ScaleMode.ScaleWithScreenSize;
            scaler.referenceResolution = new Vector2(1080, 1920);
            scaler.matchWidthOrHeight = 0.5f;

            CreateText(canvasGo.transform, "Title", "Bloom Drops", 54, TextAnchor.UpperCenter,
                new Vector2(0.5f, 1f), new Vector2(0.5f, 1f), new Vector2(0, -70), new Vector2(900, 70), FontStyle.Bold);

            var stats = CreatePanel(canvasGo.transform, "Stats", new Vector2(0.5f, 1f), new Vector2(0.5f, 1f), new Vector2(0, -160), new Vector2(960, 100));
            var hlg = stats.AddComponent<HorizontalLayoutGroup>();
            hlg.spacing = 20;
            hlg.childAlignment = TextAnchor.MiddleCenter;
            hlg.childForceExpandWidth = true;
            hlg.childForceExpandHeight = true;
            var levelText = CreateStat(stats.transform, "Level", "1");
            var movesText = CreateStat(stats.transform, "Moves", "22");
            var scoreText = CreateStat(stats.transform, "Score", "0");

            var goals = CreatePanel(canvasGo.transform, "Goals", new Vector2(0.5f, 1f), new Vector2(0.5f, 1f), new Vector2(0, -250), new Vector2(960, 60));
            var gl = goals.AddComponent<HorizontalLayoutGroup>();
            gl.spacing = 12;
            gl.childAlignment = TextAnchor.MiddleCenter;
            gl.childForceExpandWidth = false;

            var hint = CreateText(canvasGo.transform, "Hint", "Drag matching drops. Close a loop for a Bloom Burst.", 26,
                TextAnchor.LowerCenter, new Vector2(0.5f, 0f), new Vector2(0.5f, 0f), new Vector2(0, 150), new Vector2(900, 50), FontStyle.Normal);

            restartBtn = CreateButton(canvasGo.transform, "Restart", "Restart", new Vector2(0.5f, 0f), new Vector2(0.5f, 0f), new Vector2(0, 70), new Vector2(220, 70));

            var popup = CreatePanel(canvasGo.transform, "ResultPopup", new Vector2(0.5f, 0.5f), new Vector2(0.5f, 0.5f), Vector2.zero, new Vector2(720, 420));
            popup.AddComponent<Image>().color = new Color(0.07f, 0.2f, 0.22f, 0.96f);
            var popupTitle = CreateText(popup.transform, "PopupTitle", "Meadow cleared", 44, TextAnchor.MiddleCenter,
                new Vector2(0.5f, 0.5f), new Vector2(0.5f, 0.5f), new Vector2(0, 100), new Vector2(640, 60), FontStyle.Bold);
            var popupBody = CreateText(popup.transform, "PopupBody", "Nice harvest.", 28, TextAnchor.MiddleCenter,
                new Vector2(0.5f, 0.5f), new Vector2(0.5f, 0.5f), new Vector2(0, 20), new Vector2(640, 80), FontStyle.Normal);
            var continueBtn = CreateButton(popup.transform, "Continue", "Continue",
                new Vector2(0.5f, 0.5f), new Vector2(0.5f, 0.5f), new Vector2(0, -110), new Vector2(260, 70));
            popup.SetActive(false);

            result = popup.AddComponent<ResultPopup>();
            result.root = popup;
            result.titleText = popupTitle;
            result.bodyText = popupBody;
            result.buttonLabel = continueBtn.GetComponentInChildren<Text>();
            result.continueButton = continueBtn.GetComponent<Button>();

            hud = canvasGo.AddComponent<HudController>();
            hud.levelText = levelText;
            hud.movesText = movesText;
            hud.scoreText = scoreText;
            hud.hintText = hint;
            hud.goalsRoot = goals.transform;

            goalChipPrefab = CreateGoalChipPrefab();
            return canvasGo;
        }

        static GoalChip CreateGoalChipPrefab()
        {
            var root = new GameObject("GoalChip", typeof(RectTransform), typeof(HorizontalLayoutGroup), typeof(GoalChip));
            root.SetActive(false);
            Object.DontDestroyOnLoad(root);
            var hl = root.GetComponent<HorizontalLayoutGroup>();
            hl.childAlignment = TextAnchor.MiddleCenter;
            hl.spacing = 6;
            hl.padding = new RectOffset(6, 10, 4, 4);
            hl.childForceExpandWidth = false;
            hl.childForceExpandHeight = false;

            var imgGo = new GameObject("Icon", typeof(RectTransform), typeof(Image));
            imgGo.transform.SetParent(root.transform, false);
            imgGo.GetComponent<RectTransform>().sizeDelta = new Vector2(28, 28);

            var textGo = new GameObject("Label", typeof(RectTransform), typeof(Text));
            textGo.transform.SetParent(root.transform, false);
            var text = textGo.GetComponent<Text>();
            text.font = UiFont();
            text.fontSize = 18;
            text.color = Color.white;
            text.text = "0/10";
            textGo.GetComponent<RectTransform>().sizeDelta = new Vector2(64, 28);

            var chip = root.GetComponent<GoalChip>();
            chip.icon = imgGo.GetComponent<Image>();
            chip.label = text;
            return chip;
        }

        static GameObject CreatePanel(Transform parent, string name, Vector2 amin, Vector2 amax, Vector2 pos, Vector2 size)
        {
            var go = new GameObject(name, typeof(RectTransform));
            go.transform.SetParent(parent, false);
            var rt = go.GetComponent<RectTransform>();
            rt.anchorMin = amin;
            rt.anchorMax = amax;
            rt.pivot = new Vector2(0.5f, 0.5f);
            rt.anchoredPosition = pos;
            rt.sizeDelta = size;
            return go;
        }

        static Text CreateText(
            Transform parent, string name, string content, int size, TextAnchor align,
            Vector2 amin, Vector2 amax, Vector2 pos, Vector2 dim, FontStyle style)
        {
            var go = new GameObject(name, typeof(RectTransform), typeof(Text));
            go.transform.SetParent(parent, false);
            var rt = go.GetComponent<RectTransform>();
            rt.anchorMin = amin;
            rt.anchorMax = amax;
            rt.pivot = new Vector2(0.5f, 0.5f);
            rt.anchoredPosition = pos;
            rt.sizeDelta = dim;
            var t = go.GetComponent<Text>();
            t.text = content;
            t.font = UiFont();
            t.fontSize = size;
            t.alignment = align;
            t.color = Color.white;
            t.fontStyle = style;
            t.horizontalOverflow = HorizontalWrapMode.Wrap;
            t.verticalOverflow = VerticalWrapMode.Overflow;
            return t;
        }

        static Text CreateStat(Transform parent, string label, string value)
        {
            var box = new GameObject(label, typeof(RectTransform), typeof(Image), typeof(VerticalLayoutGroup));
            box.transform.SetParent(parent, false);
            box.GetComponent<Image>().color = new Color(0.05f, 0.16f, 0.18f, 0.72f);
            var v = box.GetComponent<VerticalLayoutGroup>();
            v.childAlignment = TextAnchor.MiddleCenter;
            v.padding = new RectOffset(8, 8, 8, 8);
            CreateText(box.transform, "L", label.ToUpperInvariant(), 16, TextAnchor.MiddleCenter,
                new Vector2(0.5f, 0.5f), new Vector2(0.5f, 0.5f), Vector2.zero, new Vector2(120, 24), FontStyle.Normal);
            return CreateText(box.transform, "V", value, 34, TextAnchor.MiddleCenter,
                new Vector2(0.5f, 0.5f), new Vector2(0.5f, 0.5f), Vector2.zero, new Vector2(120, 40), FontStyle.Bold);
        }

        static GameObject CreateButton(Transform parent, string name, string label, Vector2 amin, Vector2 amax, Vector2 pos, Vector2 size)
        {
            var go = new GameObject(name, typeof(RectTransform), typeof(Image), typeof(Button));
            go.transform.SetParent(parent, false);
            var rt = go.GetComponent<RectTransform>();
            rt.anchorMin = amin;
            rt.anchorMax = amax;
            rt.pivot = new Vector2(0.5f, 0.5f);
            rt.anchoredPosition = pos;
            rt.sizeDelta = size;
            go.GetComponent<Image>().color = new Color(0.98f, 0.75f, 0.14f, 1f);
            var text = CreateText(go.transform, "Text", label, 28, TextAnchor.MiddleCenter,
                Vector2.zero, Vector2.one, Vector2.zero, Vector2.zero, FontStyle.Bold);
            text.color = new Color(0.08f, 0.12f, 0.1f);
            var trt = text.GetComponent<RectTransform>();
            trt.offsetMin = Vector2.zero;
            trt.offsetMax = Vector2.zero;
            return go;
        }
    }
}
