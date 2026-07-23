---
name: Cognitive Flow
colors:
  surface: '#fdf8fd'
  surface-dim: '#ddd9de'
  surface-bright: '#fdf8fd'
  surface-container-lowest: '#ffffff'
  surface-container-low: '#f7f2f8'
  surface-container: '#f1ecf2'
  surface-container-high: '#ebe7ec'
  surface-container-highest: '#e5e1e7'
  on-surface: '#1c1b1f'
  on-surface-variant: '#494456'
  inverse-surface: '#313034'
  inverse-on-surface: '#f4eff5'
  outline: '#7a7488'
  outline-variant: '#cbc3d9'
  surface-tint: '#6d23f9'
  primary: '#4800b2'
  on-primary: '#ffffff'
  primary-container: '#6200ee'
  on-primary-container: '#d0beff'
  inverse-primary: '#cfbdff'
  secondary: '#006a60'
  on-secondary: '#ffffff'
  secondary-container: '#4af8e3'
  on-secondary-container: '#006f64'
  tertiary: '#5b3300'
  on-tertiary: '#ffffff'
  tertiary-container: '#7c4700'
  on-tertiary-container: '#ffba75'
  error: '#ba1a1a'
  on-error: '#ffffff'
  error-container: '#ffdad6'
  on-error-container: '#93000a'
  primary-fixed: '#e8ddff'
  primary-fixed-dim: '#cfbdff'
  on-primary-fixed: '#22005d'
  on-primary-fixed-variant: '#5300cd'
  secondary-fixed: '#4ffbe6'
  secondary-fixed-dim: '#17deca'
  on-secondary-fixed: '#00201c'
  on-secondary-fixed-variant: '#005048'
  tertiary-fixed: '#ffdcbe'
  tertiary-fixed-dim: '#ffb870'
  on-tertiary-fixed: '#2c1600'
  on-tertiary-fixed-variant: '#693c00'
  background: '#fdf8fd'
  on-background: '#1c1b1f'
  surface-variant: '#e5e1e7'
typography:
  display-lg:
    fontFamily: Inter
    fontSize: 57px
    fontWeight: '700'
    lineHeight: 64px
    letterSpacing: -0.25px
  headline-lg:
    fontFamily: Inter
    fontSize: 32px
    fontWeight: '600'
    lineHeight: 40px
  headline-lg-mobile:
    fontFamily: Inter
    fontSize: 28px
    fontWeight: '600'
    lineHeight: 36px
  title-lg:
    fontFamily: Inter
    fontSize: 22px
    fontWeight: '500'
    lineHeight: 28px
  body-lg:
    fontFamily: Inter
    fontSize: 16px
    fontWeight: '400'
    lineHeight: 24px
    letterSpacing: 0.5px
  body-md:
    fontFamily: Inter
    fontSize: 14px
    fontWeight: '400'
    lineHeight: 20px
    letterSpacing: 0.25px
  label-lg:
    fontFamily: Inter
    fontSize: 14px
    fontWeight: '500'
    lineHeight: 20px
    letterSpacing: 0.1px
  label-md:
    fontFamily: Inter
    fontSize: 12px
    fontWeight: '500'
    lineHeight: 16px
    letterSpacing: 0.5px
rounded:
  sm: 0.25rem
  DEFAULT: 0.5rem
  md: 0.75rem
  lg: 1rem
  xl: 1.5rem
  full: 9999px
spacing:
  base: 8px
  xs: 4px
  sm: 8px
  md: 16px
  lg: 24px
  xl: 32px
  gutter: 16px
  margin-mobile: 16px
  margin-tablet: 24px
  margin-desktop: auto
---

## Brand & Style

The design system is engineered for an **intelligent, encouraging, and gamified** academic experience. It targets a demographic spanning from middle school to university students, balancing the rigor of academic study with the engaging mechanics of modern gaming.

The style is rooted in **Modern Material 3**, utilizing its systematic approach to color and elevation while injecting a "Tech-Humanist" aesthetic. The interface emphasizes clarity through ample whitespace and focus-driven layouts, while gamified elements—such as achievement badges and XP trackers—provide a vibrant, energetic counterpoint to the neutral surfaces. The emotional response is one of **attainable mastery**: the UI feels smart enough to assist with complex tasks but friendly enough to reduce academic anxiety.

## Colors

This design system utilizes a high-vitality palette to distinguish between functional states and motivational triggers.

- **Primary (Deep Indigo):** Represents the AI's "intellect." Used for primary actions, active navigation states, and branding moments.
- **Secondary (Teal/Emerald):** Signifies "progress and success." Dedicated to completion states, checkmarks, growth charts, and positive feedback loops.
- **Tertiary (Orange/Gold):** The gamification engine. Reserved for XP gains, leveling up, streak indicators, and premium features.

The color system follows the **Material 3 Tonal Palette** logic, ensuring that in Dark Mode, colors shift to higher-vibrancy, lower-chroma variants to maintain accessibility and "glow" against dark surfaces.

## Typography

The design system relies on **Inter** to provide a clean, systematic, and highly readable experience across all technical and academic content. 

- **Display & Headlines:** Use tight letter-spacing and bold weights to create a strong hierarchy in gamified dashboards.
- **Body Text:** Optimized for long-form reading of study materials and AI explanations with a comfortable 1.5x line-height.
- **Labels:** Used for chips, button text, and small metadata. These are always set in medium weight to ensure legibility at small sizes.
- **Mobile Adaptation:** Headlines scale down on mobile to prevent awkward line breaks while maintaining a clear visual anchor for the user.

## Layout & Spacing

The design system follows a **fluid 8dp grid system** consistent with Material 3. 

- **Margins:** 16dp on mobile, 24dp on tablets. On desktop, content is constrained to a 1200px max-width container centered on the screen.
- **Gutters:** 16dp fixed gutter between cards and grid items.
- **Rhythm:** Vertical rhythm is maintained through 8dp increments. Use 24dp (lg) spacing to separate major sections and 8dp (sm) to group related elements within a card.
- **Responsive Reflow:** On mobile, lists and cards are stacked vertically. On tablet/desktop, they reflow into a multi-column grid (2-3 columns) depending on content density.

## Elevation & Depth

This design system uses **Tonal Layers** rather than heavy shadows to indicate hierarchy, adhering to the Material 3 "flat-depth" philosophy.

- **Level 0 (Surface):** The lowest layer, used for the main background.
- **Level 1 (Cards):** Used for primary content. In light mode, these use a very subtle `surface-variant` tint. In dark mode, they are slightly lighter than the background.
- **Level 2 (Floating/Active):** Used for elements that require immediate attention, like active input cards or the Bottom Navigation bar.
- **Shadows:** Only used for top-level modal sheets or Floating Action Buttons (FABs). When used, shadows are soft, diffused, and adopt a slight tint of the `primary` indigo color to avoid a "muddy" look.

## Shapes

The shape language is characterized by **smooth, generous curves** to evoke a friendly and modern feel.

- **Primary Container (Cards):** Fixed at **24px** (xl) to create a distinct, soft aesthetic that separates this from traditional corporate apps.
- **Small Components:** Buttons and Input fields use a **16px** (lg) radius.
- **Chips & Tags:** Use a **fully rounded (pill)** shape to distinguish them from interactive containers and buttons.
- **Progress Bars:** Use rounded caps to maintain the soft visual language.

## Components

- **Bottom Navigation:** Uses the standard M3 pill-shaped indicator for active states. Icons should be "Outlined" when inactive and "Filled" when active.
- **Input Cards:** Unlike standard text fields, these are prominent containers with 24px rounded corners. They should feel like a "workspace." The active state is signaled by a 2px Primary Indigo border.
- **Subject Chips:** Use a tonal background (e.g., light indigo for Math, light teal for Science) with a 12px leading icon.
- **Progress Indicators:** Linear progress bars for subject mastery; circular progress indicators for daily goal tracking. Always use the Secondary Teal color for progress.
- **Gamification Cards:** Use the Tertiary Gold for highlights. These cards may feature a subtle gradient background from the surface color to a very pale gold to indicate achievement.
- **Buttons:**
    - **Primary:** Filled Indigo with 16px corner radius.
    - **Secondary:** Outlined Teal for less urgent actions.
    - **XP/Action:** Filled Gold for "Claim Reward" or "Level Up" buttons.