---
parent: Concepts
title: Visual
nav_order: 1
layout: default
has_children: true
---

## Visual
{: .no_toc}
<details open markdown="block">
  <summary>
    Table of contents
  </summary>
  {: .text-delta }
1. TOC
{:toc}
</details>

In this section we are going to showcase the differend types of visuals in the BGW framework.
Visuals are used to style game elements in the application. Fundamentally there are two types of Visuals: 
 * [SingleLayerVisual](bgw/concepts/visual/visual.html#singlelayervisual)
 * [CompoundVisualVisual](bgw/concepts/visual/visual.html#compoundvisual)

## [SingleLayerVisual](/bgw/kotlin-docs/bgw-core/tools.aqua.bgw.visual/-single-layer-visual/)
A simple visual displaying it's type of visualization.

### [ColorVisual](/bgw/kotlin-docs/bgw-core/tools.aqua.bgw.visual/-color-visual/)
The ColorVisual displays a solid Color. It can either be created with RGB / RGBA values or via java.awt.Color. Additionally it declares static fields for various standard colors. The following statements result in the same *red* visual:
````kotlin
val visual1: ColorVisual = ColorVisual(255, 0, 0)
val visual2: ColorVisual = ColorVisual(java.awt.Color(255, 0, 0))
val visual3: ColorVisual = ColorVisual(java.awt.Color.RED)
val visual4: ColorVisual = ColorVisual.RED
````


### [TextVisual](/bgw/kotlin-docs/bgw-core/tools.aqua.bgw.visual/-text-visual/)
The TextVisual displays one line of text. It can either be created with the text to display as string and additionally an optional [Font](/bgw/kotlin-docs/bgw-core/tools.aqua.bgw.util/-font/). The following example shows the code for a TextVisual that displays "Token" in white witgh bold text in 18px size. All parameters in the font constructor are optional, so *fontStyle* for example is redundant as it contains the default value:
````kotlin
val visual: TextVisual = TextVisual("Token", Font(
  size = 18,
  color = Color.WHITE,
  family = "Arial",
  fontWeight = Font.FontWeight.BOLD,,
  fontStyle = Font.FontStyle.NORMAL
))
````

### [ImageVisual](/bgw/kotlin-docs/bgw-core/tools.aqua.bgw.visual/-image-visual/)

## [CompoundVisual](/bgw/kotlin-docs/bgw-core/tools.aqua.bgw.visual/-compound-visual/)
