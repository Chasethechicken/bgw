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

## [SingleLayerVisual](/bgw/kotlin-docs/bgw-core/tools.aqua.bgw.visual/-single-layer-visual/)
A simple visual displaying it's type of visualization.

### [ColorVisual](/bgw/kotlin-docs/bgw-core/tools.aqua.bgw.visual/-color-visual/)
The ColorVisual displays a solid Color. It can either be created with RGB / RGBA values or via java.awt.Color. Additionall,y it declares static fields for various standard colors. The following statements result in the same *red* visual:
````kotlin
val visual1: ColorVisual = ColorVisual(255, 0, 0)
val visual2: ColorVisual = ColorVisual(java.awt.Color(255, 0, 0))
val visual3: ColorVisual = ColorVisual(java.awt.Color.RED)
val visual4: ColorVisual = ColorVisual.RED
````


### [TextVisual](/bgw/kotlin-docs/bgw-core/tools.aqua.bgw.visual/-text-visual/)

### [ImageVisual](/bgw/kotlin-docs/bgw-core/tools.aqua.bgw.visual/-image-visual/)

## [CompoundVisual](/bgw/kotlin-docs/bgw-core/tools.aqua.bgw.visual/-compound-visual/)
